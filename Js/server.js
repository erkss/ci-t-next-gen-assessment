const express = require("express");

const createServer = (pool, port = 3000) => {
  const app = express();

  app.get("/posts/:id/comments", async (req, res) => {
    const postId = parseInt(req.params.id, 10);

    try {
      const client = await pool.connect();

      // Verificar se o post existe
      const postCheckQuery = "SELECT 1 FROM posts WHERE id = $1";
      const postCheckResult = await client.query(postCheckQuery, [postId]);

      if (postCheckResult.rowCount === 0) {
        client.release();
        return res.status(404).json({ error: "Post not found" });
      }

      // Buscar comentários relacionados ao post
      const commentsQuery = `
        SELECT id, text, parent_id
        FROM comments
        WHERE post_id = $1
        ORDER BY parent_id NULLS FIRST, id
      `;
      const commentsResult = await client.query(commentsQuery, [postId]);
      const comments = commentsResult.rows;

      client.release();

      // Mapeamento de comentários
      const commentMap = {};
      const rootComments = [];

      // Criando um mapa para associar id ao comentário
      comments.forEach((comment) => {
        commentMap[comment.id] = { ...comment, children: [] };
      });

      // Associando filhos aos pais
      comments.forEach((comment) => {
        if (comment.parent_id === null) {
          rootComments.push(commentMap[comment.id]);
        } else {
          if (commentMap[comment.parent_id]) {
            commentMap[comment.parent_id].children.push(commentMap[comment.id]);
          }
        }
      });

      // Função para limpar a estrutura de comentários (omitindo `children` se não houver filhos)
      const cleanComments = (comments) =>
        comments.map(({ id, text, children }) => {
          const result = { id, text };
          if (children.length > 0) {
            result.children = cleanComments(children);
          }
          return result;
        });

      // Limpar a estrutura dos comentários
      const processedComments = cleanComments(rootComments);

      // Depuração: Log da árvore final de comentários
      console.log("Processed comments:", JSON.stringify(processedComments, null, 2));

      // Retornar a resposta com os comentários processados
      return res.status(200).json({ data: processedComments });
    } catch (err) {
      console.error("[server] Error:", err);
      return res.status(500).json({ error: "Internal server error" });
    }
  });

  const server = app.listen(port, () =>
    console.log(`[server] listening on port ${port}`)
  );

  return {
    app,
    close: () =>
      new Promise((resolve) => {
        server.close(() => {
          resolve();
          console.log("[server] closed");
        });
      }),
  };
};

module.exports = { createServer };