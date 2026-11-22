# Padrões de Projeto aplicados

## 1. Singleton — `ConnectionFactory`
**Categoria:** criacional. **Propósito:** garantir que uma classe tenha **uma única instância** e oferecer um ponto global de acesso.
**Motivação:** a versão anterior abria uma conexão nova a cada operação e deixava URL/senha no código. Agora uma única fábrica lê `resources/db.properties`, guarda a conexão e a reaproveita (reabrindo se cair).
**Estrutura:** construtor `private`, atributo estático `instancia`, método `getInstance()` (synchronized, criação preguiçosa), `getConnection()`.
![UML Singleton](../uml/03-singleton.png)

**Como explicar:** "Todo DAO pede a conexão ao mesmo objeto; ninguém dá `new` na fábrica."

## 2. Decorator — `PlanoServico` / `ExtraDecorator`
**Categoria:** estrutural. **Propósito:** acrescentar responsabilidades a um objeto **dinamicamente**, sem herança.
**Motivação:** o aluno pode contratar Mensal/Trimestral/Anual + qualquer combinação de Personal, Avaliação, Aulas coletivas e Nutricionista. Com herança seriam dezenas de classes (`MensalComPersonalENutri`...). Com Decorator, cada extra "embrulha" o anterior e soma descrição e valor.
**Estrutura:** `PlanoServico` (interface) ← `PlanoBase` (componente) e `ExtraDecorator` (abstrato, guarda um `PlanoServico`) ← `PersonalTrainer`, `AvaliacaoFisica`, `AulasColetivas`, `Nutricionista`.
**Exemplo:** `new AvaliacaoFisica(new PersonalTrainer(new PlanoBase(mensal)))` = Mensal + Personal + Avaliação = R$ 259,90.
![UML Decorator](../uml/04-decorator.png)

**Como explicar:** é como empilhar bonecas russas: cada camada delega para a de dentro e acrescenta algo.

## 3. Observer — `MatriculaPublisher` / `MatriculaObserver`
**Categoria:** comportamental. **Propósito:** definir dependência **um-para-muitos**: quando o sujeito muda, todos os interessados são notificados.
**Motivação:** antes, o formulário chamava `parent.carregarProdutos()` (a tela de cadastro conhecia a de listagem). Agora o `MatriculaController` só avisa o `MatriculaPublisher`; a tabela de matrículas, o histórico de pagamentos, a barra de status e o log em arquivo se inscrevem sozinhos. Novo interessado (e-mail, SMS) = nova classe, sem mexer no controller.
**Estrutura:** Subject `MatriculaPublisher` (registrar/remover/notificar), interface `MatriculaObserver`, evento `EventoMatricula`; observadores: `MatriculaPanel`, `PagamentoPanel`, `LogMatriculaObserver` e uma lambda na barra de status.
![UML Observer](../uml/05-observer.png)

**Como explicar:** "Como assinar um canal: o controller publica; quem assinou recebe."

## Como os padrões trabalham juntos (fluxo de uma matrícula)
`MatriculaPanel` → `MatriculaController.matricular`:
1. **Decorator** monta o plano + extras e calcula o total (a tela mostra o preço em tempo real);
2. o pagamento é processado (simulado); se for recusado, nada é gravado;
3. matrícula e pagamento são gravados numa transação pelo DAO, que usa a conexão do **Singleton**;
4. **Observer** notifica telas, barra de status e log.
