INSERT INTO usuario(data_nasc, email, nome, senha, sobrenome, area_atuacao, inicio_atuacao) VALUES
('1980-09-01', 'claudio@educa.com', 'Claudio', '$2a$10$6lxREBaPXyXsQWKnq.TcJupRVne6dc3OthI4kS/ow3eqYoe0nSwpm', 'Roberto', 'Matemática', '1995-05-01'),
('1985-01-10', 'bruna@educa.com', 'Bruna', '123456', 'Oliveira', 'Química', '2000-02-12');

INSERT INTO usuario(data_nasc, email, nome, senha, sobrenome) VALUES
('2004-01-01', 'thiago@educa.com', 'Thiago', '123456', 'Mendes'),
('2004-02-02', 'brenda@educa.com', 'Brenda', '123456', 'Borges'),
('2004-03-03', 'rafaela@educa.com', 'Rafaela', '123456', 'Souza');

INSERT INTO perfil(nome) VALUES
('PROFESSOR'),
('ESTUDANTE');

INSERT INTO usuario_perfis(usuario_id, perfis_id) VALUES
(1,1),
(2,1),
(3,2),
(4,2),
(5,2);

INSERT INTO habilidade(codigo, descricao) VALUES
('EM13MAT308', 'Aplicar as relações métricas, incluindo as leis do seno e do cosseno ou as noções de congruência e semelhança, para resolver e elaborar problemas que envolvem triângulos, em variados contextos.'),
('EM13MAT306', 'Resolver e elaborar problemas em contextos que envolvem fenômenos periódicos reais (ondas sonoras, fases da lua, movimentos cíclicos, entre outros) e comparar suas representações com as funções seno e cosseno, no plano cartesiano, com ou sem apoio de aplicativos de álgebra e geometria.');

INSERT INTO conteudo(artigo, data_criacao, tempo_estimado, texto, titulo, url, autor_id, habilidade_id_habilidade) VALUES
('Campo de upload de arquivo', now(), 40, 'Relações métricas em triângulos envolve medidas em relação aos ângulos e em relação aos lados. Tradicionalmente eram assuntos do fundamental 2 como semelhança, triângulos retângulos, triângulos equiláteros, trigonometria no triângulo retângulo, pontos notáveis do triângulo, etc. Quando o autor da BNCC inclui leis do seno e cosseno supõe-se trabalhar toda a geometria plana tradicional relacionada a triângulos, já que esses conceitos relacionam lados, ângulos e trigonometria.', 'Lei dos senos e Lei dos cossenos', 'https://brasilescola.uol.com.br', 1, 1),
('Campo de upload de arquivo', now(), 20, 'Relações métricas em triângulos envolve medidas em relação aos ângulos e em relação aos lados. Tradicionalmente eram assuntos do fundamental 2 como semelhança, triângulos retângulos, triângulos equiláteros, trigonometria no triângulo retângulo, pontos notáveis do triângulo, etc. Quando o autor da BNCC inclui leis do seno e cosseno supõe-se trabalhar toda a geometria plana tradicional relacionada a triângulos, já que esses conceitos relacionam lados, ângulos e trigonometria.', 'Lei dos senos e Lei dos cossenos', 'https://brasilescola.uol.com.br', 1, 1),
('Campo de upload de arquivo', now(), 60, 'Relações métricas em triângulos envolve medidas em relação aos ângulos e em relação aos lados. Tradicionalmente eram assuntos do fundamental 2 como semelhança, triângulos retângulos, triângulos equiláteros, trigonometria no triângulo retângulo, pontos notáveis do triângulo, etc. Quando o autor da BNCC inclui leis do seno e cosseno supõe-se trabalhar toda a geometria plana tradicional relacionada a triângulos, já que esses conceitos relacionam lados, ângulos e trigonometria.', 'Lei dos senos e Lei dos cossenos', 'https://brasilescola.uol.com.br', 1, 1),
('Campo de upload de arquivo', now(), 90, 'Chamamos de um fenômeno periódico aquele que se repete sempre após o mesmo intervalo de tempo. Um exemplo mais simples de um fenômeno periódico é o dia. O movimento do Sol, que aparece pela manhã e se põe no fim da tarde até novamente aparecer de novo, determina o que chamamos de dia.', 'Fenômenos periódicos e funções seno e cosseno', 'https://brasilescola.uol.com.br', 2, 2);

INSERT INTO avaliacao(avaliacao, autor_id, conteudo_id_conteudo) VALUES
('Gostei', 1, 1),
('Não sei', 2, 1),
('Amei', 3, 1),
('Poderia melhorar', 2, 2);

INSERT INTO topico(data_criacao, descricao, status, titulo, autor_id) VALUES
(now(), 'Como fazer uma equação de segundo grau de maneira simples?', 'NAO_RESPONDIDO', 'Equação de 2º grau', 1);

INSERT INTO resposta(data_criacao, resposta, autor_id, topico_id_topico) VALUES
(now(), 'Os coeficientes da equação são: a = 4, b = 8, c = 6. Substituindo esses valores na fórmula de Bhaskara, temos: Δ = 8² – 4.4.6 Δ = 64 – 96 Δ = – 32 Como Δ < 0, a equação não possui raiz real.', 2, 1),
(now(), 'Encontre as raízes da equação: x2 – 4x – 5 = 0 RE:Os coeficientes dessa equação são: a = 1, b = – 4, c = – 5. Agora basta aplicar esses valores na fórmula de Bhaskara: Δ = (– 4)² – 4.1.(– 5)  Δ = 16 + 20 Δ = 36 x = – (– 4) ± √36 2.1 x = 4 ± 6 2 x = 10 = 52x = – 2 = – 1 2 Nesse caso, a equação tem duas raízes reais: – 1 e 5.', 3, 1);
