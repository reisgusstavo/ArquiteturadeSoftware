#!/bin/sh
# Cria o repositório (se preciso) e organiza o projeto em commits na ordem lógica de construção.
# Uso (na raiz do projeto):  sh scripts/git-historico.sh
set -e
cd "$(dirname "$0")/.."
[ -d .git ] || git init -b main

c() { msg="$1"; shift; git add -- "$@"; git commit -q -m "$msg"; echo "commit: $msg"; }

c "chore: estrutura inicial, .gitignore, scripts de execução e driver JDBC" \
  .gitignore README.md scripts/compilar.sh scripts/compilar.bat scripts/executar.sh scripts/executar.bat \
  scripts/demo.sh scripts/demo.bat lib resources/image/.gitkeep resources/logs/.gitkeep
c "feat(db): script do banco dbacademia e modelo de configuração" \
  src/dao/dbAcademia.sql resources/db.properties.example
c "feat(model): entidades, enum de pagamento, exceções e utilitários" \
  src/model src/util
c "feat(singleton): ConnectionFactory com instância única e credenciais externas" \
  src/dao/ConnectionFactory.java
c "feat(dao): interfaces e implementações JDBC" \
  src/dao
c "feat(decorator): plano base e extras (personal, avaliação, aulas, nutri)" \
  src/pattern/decorator
c "feat(observer): publisher, evento e observador de log" \
  src/pattern/observer
c "feat(controller): regras de negócio de alunos, matrícula e pagamento" \
  src/controller
c "feat(view): telas Swing e ponto de entrada" \
  src/view src/Main.java
c "test: demonstração dos padrões e teste de integração" \
  src/PadroesDemo.java src/IntegracaoTeste.java
c "docs: padrões, diagramas UML e arquitetura" \
  doc resources/image scripts/git-historico.sh

if [ -n "$(git status --porcelain)" ]; then
  echo "Atenção: há arquivos fora dos commits:"; git status --short
fi
echo "Pronto. Histórico:"; git log --oneline
