# Arquitetura do sistema (UML)

Os diagramas ficam em `doc/uml/` como **imagens (.png)** e como código-fonte **PlantUML (.puml)**, que pode ser editado e
renderizado de novo (extensão *PlantUML* do VS Code ou https://www.plantuml.com/plantuml).
Os diagramas individuais de cada padrão estão em [`UML.md`](UML.md).

## 1. Diagrama de pacotes (camadas)
![Camadas](01-camadas.png)

Regra de dependência: de cima para baixo. A `view` nunca acessa o `dao`; SQL só existe nas classes `*DAOJdbc`.

## 2. Diagrama de classes (visão geral)
![Classes](02-classes.png)

## 3. Diagrama de sequência — caso de uso "matricular e pagar"
![Sequência](06-sequencia-matricular.png)

## 4. Modelo de dados (banco `dbacademia`)
![ER](07-banco-er.png)

## 5. Organização do repositório (mesmo molde do projeto UNIVEL)
```
academia/
├── README.md
├── .gitignore
├── doc/
│   ├── txt/   sistemaAcademia.txt, PADROES.md
│   └── uml/   diagramas .puml e .png, ARQUITETURA.md, UML.md
├── lib/
│   └── conn.jar                 # driver JDBC do MySQL
├── resources/
│   ├── image/
│   ├── logs/                    # matriculas.log (ignorado pelo git)
│   └── db.properties.example    # o db.properties real NÃO é versionado
├── scripts/                     # compilar, executar, demo (.sh e .bat) e git-historico.sh
└── src/
    ├── Main.java
    ├── PadroesDemo.java
    ├── IntegracaoTeste.java
    ├── model/        Aluno, Plano, Matricula, Pagamento, MetodoPagamento
    ├── dao/          ConnectionFactory (Singleton), interfaces *DAO, *DAOJdbc, dbAcademia.sql
    ├── controller/   AlunoController, MatriculaController, PagamentoController
    ├── util/         NegocioException, PersistenciaException, Moeda, ResultadoPagamento
    ├── view/         MainFrame, AlunoPanel, MatriculaPanel, PagamentoPanel
    └── pattern/
        ├── decorator/
        └── observer/
```
Convenções: branch principal `main`; mensagens de commit no formato `tipo(escopo): descrição`
(`feat`, `fix`, `docs`, `chore`, `test`); nunca commitar senha nem a pasta `out/`.
