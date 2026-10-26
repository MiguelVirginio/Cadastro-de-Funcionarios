Interface gráfica

JavaFX \+ FXML

Regras de negócio

Spring Boot \+ Services

Persistência dos dados

Spring Data JPA \+ Hibernate \+ MySQL

## 1\. Qual é o objetivo do sistema?

O sistema permite cadastrar funcionários e autenticar usuários por meio de uma interface gráfica.

O fluxo principal é:

1\. Tela de cadastro ou login

2\. Controller JavaFX

Recebe os dados da interface

3\. Service

Valida e executa as regras de negócio

4\. Repository \+ JPA

Consulta ou grava os dados

5\. Banco de dados MySQL

Importante: o JavaFX cuida da interface. O Spring administra os componentes e suas dependências. O JPA/Hibernate faz a comunicação entre os objetos Java e as tabelas do banco.

## 2\. Como utilizar o programa

### 2.1. Iniciar a aplicação

1. Abra o projeto no IntelliJ IDEA.
2. Verifique se o MySQL está em execução e se o banco `cadastroteste` está acessível.
3. Confira as configurações em `application.properties`.
4. Execute a classe `HelloApplication`.

Ao iniciar, o programa abre o contexto do Spring Boot em modo não web, usando `WebApplicationType.NONE`. Isso significa que o Spring gerencia os serviços e os repositórios sem precisar iniciar um servidor HTTP para a aplicação desktop.

### 2.2. Cadastrar um funcionário

Preencha os campos da tela de cadastro:

| Campo | Finalidade |
| --- | --- |
| Nome | Primeiro nome do funcionário |
| Sobrenome | Sobrenome do funcionário |
| E-mail | Identificação utilizada no login |
| Senha | Credencial de acesso |

O comportamento configurado é:

1. O usuário preenche os campos.
2. O controller verifica se os campos obrigatórios estão preenchidos.
3. O e-mail passa pela validação de formato.
4. O controller chama o `FuncionarioService`.
5. O serviço verifica as regras de cadastro, incluindo a existência de e-mail duplicado.
6. A senha é processada pelo `PasswordEncoder` antes de ser armazenada.
7. O repositório solicita a persistência do funcionário.
8. O Hibernate executa a operação no MySQL.
9. Se o cadastro for concluído, a aplicação informa o sucesso e encaminha o usuário para o login.

Se alguma validação falhar, o cadastro deve ser interrompido e a interface deve apresentar o erro correspondente.

### 2.3. Fazer login

1. Informe o e-mail cadastrado.
2. Informe a senha.
3. Clique no botão de login ou pressione Enter, conforme configurado na tela.
4. O controller chama o serviço de autenticação.
5. O serviço procura o funcionário pelo e-mail e verifica a senha com o mecanismo de comparação apropriado.
6. Se as credenciais forem válidas, o usuário segue para a tela de sucesso.
7. Se forem inválidas, a aplicação apresenta uma mensagem de erro.

A tela também possui suporte para alternar a visibilidade da senha.

Atenção: essa autenticação é a lógica da aplicação desktop. Ela não significa, por si só, que já existe uma sessão de segurança completa, autorização por perfil ou autenticação por token.

## 3\. O que cada classe faz

A divisão de responsabilidades ajuda a manter o projeto organizado e facilita futuras alterações.

| Classe ou componente | Responsabilidade |
| --- | --- |
| `HelloApplication` | Inicia a interface JavaFX e integra a inicialização com o Spring. |
| `AplicacaoSpring` | Configura a inicialização do contexto Spring Boot. |
| `HelloController` | Controla a tela de cadastro e recebe as ações do usuário. |
| `LoginController` | Controla a tela de login e o fluxo de autenticação. |
| `FuncionarioService` | Centraliza as regras de cadastro e autenticação. |
| `FuncionarioRepository` | Executa consultas e operações de persistência usando Spring Data JPA. |
| `Funcionario` | Representa o funcionário como entidade persistida no banco. |
| `PasswordConfig` | Configura o mecanismo de codificação e verificação de senhas. |
| Arquivos FXML | Definem a estrutura visual das telas. |
| `module-info.java` | Declara os módulos Java exigidos e as permissões de acesso reflexivo. |
| `application.properties` | Define as propriedades de conexão e comportamento do JPA. |

Os nomes dos métodos, as validações exatas e o comportamento de cada tela devem ser confirmados no código atual. A tabela descreve as responsabilidades da arquitetura que configuramos.

### Por que separar Controller, Service e Repository?

- Controller: sabe o que fazer quando o usuário clica em um botão.
- Service: sabe quais regras precisam ser respeitadas.
- Repository: sabe como acessar os dados persistidos.

Por exemplo, a tela não deve executar SQL diretamente. Ela solicita ao serviço que faça o cadastro, e o serviço utiliza o repositório.

Isso reduz o acoplamento e torna o sistema mais fácil de testar.

## 4\. Como os dados são armazenados

A entidade `Funcionario` representa a tabela `funcionarios`.

| Campo Java | Tipo | Comportamento |
| --- | --- | --- |
| `id` | `Long` | Identificador gerado pelo banco. |
| `nome` | `String` | Obrigatório, até 100 caracteres. |
| `sobrenome` | `String` | Obrigatório, até 100 caracteres. |
| `email` | `String` | Obrigatório, até 180 caracteres. |
| `senhaHash` | `String` | Armazena o valor codificado da senha, até 100 caracteres. |
| `dataCadastro` | `LocalDateTime` | Data e hora definidas antes da primeira persistência. |

A entidade utiliza anotações JPA para definir o mapeamento:

- `@Entity`: informa que a classe é uma entidade persistente.
- `@Table`: define o nome da tabela e a restrição de unicidade do e-mail.
- `@Id`: identifica a chave primária.
- `@GeneratedValue(strategy = GenerationType.IDENTITY)`: delega a geração do identificador ao banco.
- `@Column`: define restrições e propriedades das colunas.
- `@PrePersist`: executa a inicialização da data antes da primeira gravação.

### O que acontece quando o funcionário é salvo?

O código Java trabalha com um objeto:

Java

```
Funcionario funcionario = new Funcionario(
    nome,
    sobrenome,
    email,
    senhaCodificada
);
```

O serviço encaminha esse objeto ao repositório, normalmente por meio de `save()`.

Java

```
funcionarioRepository.save(funcionario);
```

O Spring Data JPA delega a persistência ao Hibernate. O Hibernate converte a operação em SQL, e o driver JDBC envia a instrução ao MySQL.

O exemplo ilustra o fluxo; os nomes das variáveis podem diferir do código real.

### Como o sistema impede e-mails duplicados?

Existem duas camadas que podem atuar em conjunto:

1. O serviço consulta o repositório para verificar se o e-mail já está cadastrado.
2. O banco possui uma restrição de unicidade para o e-mail.

A primeira camada permite mostrar uma mensagem amigável. A segunda protege a integridade dos dados, inclusive quando duas operações tentam cadastrar o mesmo endereço simultaneamente.

Detalhe importante: a restrição de unicidade do MySQL pode diferenciar maiúsculas e minúsculas ou não, dependendo da configuração de comparação da coluna. O método `findByEmailIgnoreCase` ajuda nas consultas sem distinção de caixa, mas não garante sozinho que o banco impeça duplicatas como `Pessoa@exemplo.com` e `pessoa@exemplo.com`.

## 5\. Configuração do banco de dados

O arquivo `src/main/resources/application.properties` centraliza a configuração:

properties

`spring.docker.compose.enabled=false spring.datasource.url=jdbc:mysql://localhost:1112/cadastroteste spring.datasource.username=cpd spring.datasource.password=${DB_PASSWORD} spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver spring.jpa.hibernate.ddl-auto=update spring.jpa.show-sql=true`

A variável `${DB_PASSWORD}` é uma forma de exemplo para evitar colocar a senha diretamente no arquivo. Ela precisa estar definida no ambiente ou ser fornecida por outro mecanismo de configuração; caso contrário, a conexão pode falhar.

O significado das principais propriedades:

| Propriedade | Efeito |
| --- | --- |
| `spring.docker.compose.enabled=false` | Desativa a integração automática de Docker Compose do Spring Boot. |
| `spring.datasource.url` | Informa o endereço, a porta e o banco de dados. |
| `spring.datasource.username` | Define o usuário do MySQL. |
| `spring.datasource.password` | Fornece a senha desse usuário. |
| `spring.datasource.driver-class-name` | Identifica o driver JDBC do MySQL. |
| `spring.jpa.hibernate.ddl-auto=update` | Solicita ao Hibernate atualizar o esquema para refletir os mapeamentos das entidades. |
| `spring.jpa.show-sql=true` | Exibe comandos SQL gerados pelo Hibernate nos logs. |

### O que significa `ddl-auto=update`?

Ao iniciar, o Hibernate compara os mapeamentos das entidades com a estrutura do banco e tenta aplicar as atualizações necessárias.

Isso é conveniente durante o desenvolvimento, mas não substitui uma estratégia de migração controlada. Em produção, considere utilizar ferramentas como Flyway ou Liquibase e planejar as alterações do esquema.

O `show-sql=true` também é útil para estudar o funcionamento do JPA, mas pode gerar muitos logs. Em ambientes de produção, avalie desativá-lo.

## 6\. Por que precisamos corrigir o acesso dos módulos Java?

O projeto usa `module-info.java`, portanto as classes não ficam automaticamente acessíveis a qualquer módulo.

O Spring utiliza reflexão e, em algumas situações, gera classes dinamicamente por meio do CGLIB. O erro que apareceu ao iniciar o `FuncionarioService` ocorria porque o módulo `java.base` não permitia o acesso reflexivo necessário ao pacote `java.lang`.

A opção adicionada às configurações de execução foi:

Plain text

`--add-opens=java.base/java.lang=spring.core`

Ela permite que o módulo `spring.core` acesse reflexivamente os membros necessários de `java.lang`.

Como manter essa configuração:

1. Abra `Run` → `Edit Configurations` no IntelliJ IDEA.
2. Selecione a configuração que executa `HelloApplication`.
3. Localize o campo `VM options`.
4. Mantenha a opção acima.
5. Salve e execute novamente.

Essa opção deve estar presente na configuração de execução utilizada. Se você executar o programa por outro mecanismo, como um comando Java separado ou uma configuração Maven específica, talvez precise replicá-la.

Ela resolve o problema de acesso reflexivo identificado, mas não substitui as diretivas `opens` necessárias para os pacotes da própria aplicação.

## 7\. Como o Spring encontra e conecta as classes

O Spring Boot cria e administra objetos chamados beans. Em vez de instanciar manualmente cada serviço, o framework pode criá-los e injetar suas dependências.

Por exemplo, o `FuncionarioService` depende do `FuncionarioRepository` e do `PasswordEncoder`. Quando a configuração está correta, o Spring fornece essas dependências automaticamente pelo construtor.

Isso é chamado de injeção de dependência.

O fluxo é semelhante a:

1\. Spring inicializa o contexto

Lê configurações e identifica componentes.

2\. Spring configura o repositório

Prepara o acesso ao banco via JPA.

3\. Spring cria o serviço

Injeta repositório e codificador de senhas.

4\. JavaFX utiliza os controllers

Os controllers podem receber serviços gerenciados pelo Spring por meio do FXMLLoader.

A integração entre JavaFX e Spring é importante: se o `FXMLLoader` criar os controllers diretamente, fora do Spring, eles podem não receber a injeção de dependências. A fábrica de controllers baseada em `contextoSpring::getBean` permite que o Spring os gerencie.

## 8\. Cuidados de segurança

O projeto já tem elementos importantes, mas vale distinguir o que está configurado do que precisa ser confirmado.

- Senha: deve ser armazenada usando um algoritmo apropriado para senhas, como BCrypt, e nunca em texto puro. O `PasswordEncoder` deve codificar e verificar as credenciais corretamente.
- E-mail: deve ter validação de formato e uma política consistente de unicidade.
- Credenciais do banco: evite manter senhas reais versionadas no Git.
- Banco de dados: limite as permissões do usuário MySQL às operações necessárias.
- Mensagens de erro: evite exibir detalhes internos, SQL ou credenciais ao usuário final.
- Autenticação: a tela de login não substitui o controle de autorização das operações. Se o sistema crescer, será necessário definir quais usuários podem realizar cada ação.

## 9\. Problemas comuns e como diagnosticar

| Problema | O que verificar |
| --- | --- |
| Falha ao conectar ao MySQL | Serviço ativo, porta `1112`, nome `cadastroteste`, usuário, senha e permissões. |
| `Could not generate CGLIB subclass` | A opção `--add-opens` e as configurações de módulos Java. |
| `No qualifying bean` | Se a classe tem a anotação apropriada e está dentro do escopo de descoberta do Spring. |
| Erro ao carregar FXML | Caminho do arquivo, `fx:controller`, nomes dos campos e métodos do controller. |
| Cadastro não persiste | Logs do Hibernate, restrições da tabela e exceções do repositório. |
| E-mail duplicado | Consulta do serviço e restrição de unicidade no MySQL. |
| Erro de validação da senha | Configuração do `PasswordEncoder` e comparação entre senha informada e hash armazenado. |

Quando o erro aparecer, consulte primeiro o primeiro `Caused by` relevante no console. A última exceção costuma indicar a causa técnica mais específica.

## 10\. Resumo: como o sistema se comporta

O fluxo completo fica assim:

- Inicialização: JavaFX abre a interface e o Spring prepara os componentes.
- Cadastro: o controller recebe os campos, o serviço valida as regras e o repositório persiste o funcionário.
- Persistência: Hibernate transforma as operações JPA em SQL e o MySQL armazena os dados.
- Login: o serviço localiza o funcionário e verifica a senha codificada.
- Erros: falhas de validação, conexão ou persistência devem ser tratadas e apresentadas de forma adequada.
- Encerramento: a aplicação deve fechar o contexto Spring quando a janela principal for encerrada, liberando os recursos gerenciados.

Essa arquitetura separa interface, regras de negócio e persistência, facilitando a manutenção e a evolução do projeto.

Limite desta documentação: ela descreve a arquitetura e as configurações que conhecemos da aplicação. Para documentar cada método real, cada tela e o comportamento exato de todas as exceções, ainda seria necessário revisar a versão atual dos arquivos-fonte.