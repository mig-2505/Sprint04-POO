-- SCRIPT DE CARGA DE DADOS DE TESTE (DML)

-- Inserindo Trechos
INSERT INTO tb_trecho_rodovia (km, altura_vegetacao, umidade, possui_sensor) VALUES (10, 1.8, 75.0, 1);
INSERT INTO tb_trecho_rodovia (km, altura_vegetacao, umidade, possui_sensor) VALUES (25, 0.8, 40.0, 0);
INSERT INTO tb_trecho_rodovia (km, altura_vegetacao, umidade, possui_sensor) VALUES (42, 1.2, 55.5, 1);
INSERT INTO tb_trecho_rodovia (km, altura_vegetacao, umidade, possui_sensor) VALUES (60, 2.1, 80.0, 1);

-- Inserindo Equipes
INSERT INTO tb_equipe_manutencao (nome, especialidade, disponivel) VALUES ('Equipe Alpha', 'Roçada Mecanizada', 1);
INSERT INTO tb_equipe_manutencao (nome, especialidade, disponivel) VALUES ('Equipe Beta', 'Pulverização', 1);
INSERT INTO tb_equipe_manutencao (nome, especialidade, disponivel) VALUES ('Equipe Gama', 'Roçada Mecanizada', 0);

-- Inserindo Intervenções Prévias
INSERT INTO tb_intervencao_operacional (tipo, km_trecho, status) VALUES ('Roçada Mecanizada', 10, 'Concluído');
INSERT INTO tb_intervencao_operacional (tipo, km_trecho, status) VALUES ('Pulverização', 60, 'Pendente');

COMMIT;