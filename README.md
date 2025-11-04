# 🧭 Jobbi – Conectando talentos e oportunidades

O **Jobbi** é um aplicativo desenvolvido para facilitar a conexão entre estudantes em busca de estágio e empresas que oferecem oportunidades. A proposta é simplificar o processo de busca, candidatura e gestão de vagas, trazendo uma experiência intuitiva tanto para o estudante quanto para o recrutador.

## 🚀 Principais funcionalidades

- ✅ **Cadastro de perfis** de estudantes e empresas
- ✅ **Publicação e gerenciamento** de vagas de estágio
- ✅ **Busca inteligente** de oportunidades com base no perfil do usuário
- ✅ **Acompanhamento do status** das candidaturas
- ✅ **Interface moderna e responsiva** desenvolvida com Jetpack Compose (Kotlin)
- ✅ **Autenticação segura** com Firebase Authentication
- ✅ **Banco de dados em tempo real** com Firebase Firestore

## 🛠️ Tecnologias utilizadas

- **Kotlin** + **Jetpack Compose**
- **Firebase Authentication** (autenticação de usuários)
- **Firebase Firestore** (banco de dados NoSQL em tempo real)
- **MVVM Architecture** (Model-View-ViewModel)
- **Coroutines** / **Flow** (programação assíncrona)
- **Material Design 3** (design system)
- **Navigation Compose** (navegação entre telas)

## 📋 Pré-requisitos

Antes de começar, certifique-se de ter instalado:

- **Android Studio** (versão Hedgehog ou superior)
- **JDK 17** ou superior
- **Gradle 8.2+**
- **Conta Google** (para configurar o Firebase)

## 🔧 Configuração do Projeto

### 1. Clone o repositório

```bash
git clone <seu-repositorio>
cd agenda_estagio_project
```

### 2. Configure o Firebase

O projeto já vem com um arquivo `google-services.json` de exemplo, mas você precisa substituí-lo pelo seu próprio:

#### Passo a passo:

1. Acesse o [Firebase Console](https://console.firebase.google.com/)
2. Crie um novo projeto ou use um existente
3. Adicione um app Android ao projeto:
   - **Package name**: `com.agenda.estagio`
   - Baixe o arquivo `google-services.json`
4. Substitua o arquivo em `app/google-services.json` pelo arquivo baixado

#### Configurar Authentication:

1. No Firebase Console, vá em **Authentication** > **Sign-in method**
2. Ative o provedor **Email/Password**

#### Configurar Firestore:

1. No Firebase Console, vá em **Firestore Database**
2. Clique em **Create database**
3. Escolha o modo **Test mode** (para desenvolvimento)
4. Selecione uma região próxima

### 3. Abra o projeto no Android Studio

1. Abra o Android Studio
2. Selecione **Open** e navegue até a pasta do projeto
3. Aguarde o Gradle sincronizar as dependências

### 4. Execute o aplicativo

1. Conecte um dispositivo Android ou inicie um emulador
2. Clique em **Run** (▶️) ou pressione `Shift + F10`

## 📱 Estrutura do Projeto

```
app/
├── src/main/java/com/agenda/estagio/
│   ├── data/
│   │   ├── models/          # Data classes (User, Vaga, Candidatura)
│   │   ├── repository/      # Repositórios para acesso ao Firebase
│   │   └── viewmodel/       # ViewModels (AuthViewModel, VagaViewModel, etc.)
│   ├── navigation/          # Configuração de navegação
│   ├── screens/             # Telas de login e cadastro
│   ├── ui/
│   │   ├── screens/         # Telas principais do app
│   │   │   ├── aluno/       # Telas do aluno
│   │   │   ├── empresa/     # Telas da empresa
│   │   │   └── admin/       # Telas do admin
│   │   └── theme/           # Tema e cores do app
│   └── MainActivity.kt      # Activity principal
```

## 🗄️ Estrutura do Banco de Dados (Firestore)

### Coleção: `users`

Armazena dados de alunos e empresas.

```json
{
  "id": "userId123",
  "email": "usuario@email.com",
  "nome": "Nome do Usuário",
  "tipo": "ALUNO" | "EMPRESA",
  "telefone": "(11) 99999-9999",
  
  // Campos específicos de ALUNO
  "cpf": "123.456.789-00",
  "dataNascimento": "01/01/2000",
  "curso": "Ciência da Computação",
  "instituicao": "Universidade XYZ",
  "periodo": "5º Semestre",
  
  // Campos específicos de EMPRESA
  "cnpj": "12.345.678/0001-00",
  "razaoSocial": "Empresa LTDA",
  "nomeFantasia": "Empresa",
  "setor": "Tecnologia",
  "cidade": "São Paulo",
  "estado": "SP"
}
```

### Coleção: `vagas`

Armazena as vagas publicadas pelas empresas.

```json
{
  "id": "vagaId123",
  "empresaId": "userId123",
  "empresaNome": "Empresa XYZ",
  "titulo": "Desenvolvedor Mobile",
  "descricao": "Descrição da vaga...",
  "area": "Tecnologia",
  "requisitos": "Conhecimento em Kotlin...",
  "beneficios": "Vale transporte, alimentação...",
  "cargaHoraria": "6h/dia",
  "bolsa": "R$ 1.500,00",
  "localizacao": "São Paulo, SP",
  "modalidade": "Presencial" | "Remoto" | "Híbrido",
  "status": "ATIVA" | "PAUSADA" | "ENCERRADA",
  "numeroVagas": 2,
  "criadoEm": 1234567890,
  "atualizadoEm": 1234567890
}
```

### Coleção: `candidaturas`

Armazena as candidaturas dos alunos às vagas.

```json
{
  "id": "candidaturaId123",
  "vagaId": "vagaId123",
  "vagaTitulo": "Desenvolvedor Mobile",
  "empresaId": "empresaId123",
  "empresaNome": "Empresa XYZ",
  "alunoId": "alunoId123",
  "alunoNome": "João Silva",
  "alunoEmail": "joao@email.com",
  "alunoCurso": "Ciência da Computação",
  "alunoInstituicao": "Universidade XYZ",
  "status": "PENDENTE" | "EM_ANALISE" | "APROVADA" | "REJEITADA" | "CANCELADA",
  "mensagem": "Mensagem opcional do aluno",
  "criadoEm": 1234567890,
  "atualizadoEm": 1234567890
}
```

## 👥 Fluxos de Uso

### Para Alunos:

1. **Cadastro**: Preencher dados pessoais e acadêmicos
2. **Login**: Acessar com email e senha
3. **Buscar vagas**: Navegar pelas vagas disponíveis
4. **Candidatar-se**: Enviar candidatura para vagas de interesse
5. **Acompanhar**: Ver status das candidaturas enviadas

### Para Empresas:

1. **Cadastro**: Preencher dados da empresa
2. **Login**: Acessar com email e senha
3. **Publicar vagas**: Criar novas oportunidades de estágio
4. **Gerenciar vagas**: Editar ou deletar vagas publicadas
5. **Ver candidatos**: Acompanhar candidaturas recebidas

## 🧪 Testando o Aplicativo

### Criar usuários de teste:

#### Aluno de teste:
- Email: `aluno@teste.com`
- Senha: `123456`
- Cadastre-se pela tela de cadastro de aluno

#### Empresa de teste:
- Email: `empresa@teste.com`
- Senha: `123456`
- Cadastre-se pela tela de cadastro de empresa

### Fluxo de teste completo:

1. **Cadastre uma empresa** e faça login
2. **Publique algumas vagas** de estágio
3. **Faça logout** e cadastre um aluno
4. **Navegue pelas vagas** e candidate-se
5. **Volte para a conta da empresa** e veja as candidaturas

## 🎯 Objetivo

Proporcionar uma ponte eficiente entre quem procura uma chance e quem busca novos talentos, incentivando o desenvolvimento profissional de jovens e a modernização do recrutamento de estágios.

## 📝 Notas Importantes

- ⚠️ O arquivo `google-services.json` incluído é apenas um exemplo. **Você deve substituí-lo pelo seu próprio arquivo do Firebase**.
- 🔒 As regras de segurança do Firestore estão em modo de teste. Para produção, configure regras adequadas.
- 🚀 Este é um projeto educacional/demonstrativo. Para uso em produção, implemente validações adicionais e tratamento de erros.

## 🤝 Contribuindo

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou pull requests.

## 📄 Licença

Este projeto é de código aberto e está disponível sob a licença MIT.

---

**Desenvolvido com ❤️ usando Kotlin e Jetpack Compose**
