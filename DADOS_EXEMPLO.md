# 📊 Dados de Exemplo para Teste

Este documento contém dados de exemplo que você pode usar para testar o aplicativo Jobbi.

## 👤 Usuários de Teste

### Aluno 1
```
Email: aluno1@teste.com
Senha: teste123
Nome: João Silva
CPF: 123.456.789-00
Telefone: (11) 98765-4321
Data de Nascimento: 15/03/2002
Curso: Ciência da Computação
Instituição: Universidade de São Paulo (USP)
Período: 5º Semestre
```

### Aluno 2
```
Email: aluno2@teste.com
Senha: teste123
Nome: Maria Santos
CPF: 987.654.321-00
Telefone: (11) 91234-5678
Data de Nascimento: 20/07/2001
Curso: Engenharia de Software
Instituição: Universidade Estadual de Campinas (UNICAMP)
Período: 6º Semestre
```

### Empresa 1
```
Email: empresa1@teste.com
Senha: teste123
Nome Fantasia: TechCorp Brasil
Razão Social: TechCorp Tecnologia LTDA
CNPJ: 12.345.678/0001-90
Telefone: (11) 3456-7890
Setor: Tecnologia da Informação
Cidade: São Paulo
Estado: SP
```

### Empresa 2
```
Email: empresa2@teste.com
Senha: teste123
Nome Fantasia: Creative Studio
Razão Social: Creative Design Studio LTDA
CNPJ: 98.765.432/0001-10
Telefone: (21) 2345-6789
Setor: Design e Marketing
Cidade: Rio de Janeiro
Estado: RJ
```

---

## 💼 Vagas de Exemplo

Após criar as contas de empresa, publique estas vagas:

### Vaga 1 - TechCorp Brasil
```
Título: Desenvolvedor Mobile Android
Área: Tecnologia
Descrição: Buscamos estudante de tecnologia para atuar no desenvolvimento de aplicativos Android usando Kotlin e Jetpack Compose.
Requisitos:
- Cursando Ciência da Computação, Engenharia de Software ou áreas relacionadas
- Conhecimento em Kotlin
- Familiaridade com Git/GitHub
- Proatividade e vontade de aprender

Benefícios:
- Vale transporte
- Vale refeição
- Auxílio educação
- Ambiente descontraído

Carga Horária: 6 horas/dia
Bolsa: R$ 1.800,00
Modalidade: Híbrido
Cidade: São Paulo
Estado: SP
Número de Vagas: 2
```

### Vaga 2 - TechCorp Brasil
```
Título: Analista de Dados Júnior
Área: Análise de Dados
Descrição: Oportunidade para estudantes que desejam atuar com análise de dados, criação de dashboards e geração de insights.
Requisitos:
- Cursando Estatística, Ciência da Computação ou áreas relacionadas
- Conhecimento em Python ou R
- Noções de SQL
- Excel avançado

Benefícios:
- Vale transporte
- Vale refeição
- Plano de saúde
- Treinamentos

Carga Horária: 6 horas/dia
Bolsa: R$ 1.600,00
Modalidade: Remoto
Cidade: São Paulo
Estado: SP
Número de Vagas: 1
```

### Vaga 3 - Creative Studio
```
Título: Designer UI/UX
Área: Design
Descrição: Procuramos estudante criativo para auxiliar na criação de interfaces de aplicativos e websites.
Requisitos:
- Cursando Design, Publicidade ou áreas relacionadas
- Conhecimento em Figma
- Portfolio com trabalhos anteriores
- Senso estético apurado

Benefícios:
- Vale transporte
- Vale refeição
- Horário flexível
- Ambiente criativo

Carga Horária: 4 horas/dia
Bolsa: R$ 1.200,00
Modalidade: Presencial
Cidade: Rio de Janeiro
Estado: RJ
Número de Vagas: 1
```

### Vaga 4 - Creative Studio
```
Título: Social Media
Área: Marketing Digital
Descrição: Estágio em marketing digital com foco em gestão de redes sociais e criação de conteúdo.
Requisitos:
- Cursando Marketing, Publicidade ou Comunicação
- Conhecimento em redes sociais
- Criatividade para criação de conteúdo
- Boa comunicação escrita

Benefícios:
- Vale transporte
- Vale refeição
- Bonificação por performance
- Cursos online

Carga Horária: 6 horas/dia
Bolsa: R$ 1.400,00
Modalidade: Híbrido
Cidade: Rio de Janeiro
Estado: RJ
Número de Vagas: 2
```

---

## 🧪 Fluxo de Teste Completo

### Passo 1: Criar Empresas
1. Abra o app e selecione **"Empresa"**
2. Cadastre a **Empresa 1** com os dados acima
3. Faça logout
4. Cadastre a **Empresa 2** com os dados acima

### Passo 2: Publicar Vagas
1. Faça login como **Empresa 1**
2. Publique as **Vagas 1 e 2**
3. Faça logout
4. Faça login como **Empresa 2**
5. Publique as **Vagas 3 e 4**
6. Faça logout

### Passo 3: Criar Alunos
1. Selecione **"Aluno"**
2. Cadastre o **Aluno 1** com os dados acima
3. Faça logout
4. Cadastre o **Aluno 2** com os dados acima

### Passo 4: Candidatar-se
1. Como **Aluno 1**, navegue pelas vagas
2. Candidate-se para **Vaga 1** e **Vaga 3**
3. Faça logout
4. Como **Aluno 2**, candidate-se para **Vaga 2** e **Vaga 4**

### Passo 5: Verificar Candidaturas
1. Faça login como **Empresa 1**
2. Veja as candidaturas recebidas
3. Faça login como **Aluno 1**
4. Veja suas candidaturas na aba "Minhas"

---

## 🔍 Verificar no Firebase Console

Após executar os testes, você pode verificar os dados no Firebase Console:

### Authentication > Users
Você deve ver 4 usuários:
- aluno1@teste.com
- aluno2@teste.com
- empresa1@teste.com
- empresa2@teste.com

### Firestore Database > Data

#### Coleção `users`
Deve conter 4 documentos (2 alunos + 2 empresas)

#### Coleção `vagas`
Deve conter 4 documentos (vagas publicadas)

#### Coleção `candidaturas`
Deve conter 4 documentos (candidaturas enviadas)

---

## 📝 Notas

- Todos os dados acima são **fictícios** e apenas para teste
- As senhas são simples (`teste123`) apenas para facilitar os testes
- Em produção, use senhas fortes e dados reais
- Você pode modificar os dados conforme necessário

---

## 🎯 Cenários de Teste

### Teste 1: Busca de Vagas
- Como aluno, busque por "Desenvolvedor"
- Deve aparecer a vaga de Desenvolvedor Mobile

### Teste 2: Filtro por Área
- Navegue pelas vagas e observe as diferentes áreas
- Tecnologia, Design, Marketing, Análise de Dados

### Teste 3: Candidatura Duplicada
- Tente se candidatar duas vezes para a mesma vaga
- Deve aparecer erro: "Você já se candidatou a esta vaga"

### Teste 4: Gerenciamento de Vagas
- Como empresa, edite uma vaga publicada
- Delete uma vaga
- Crie uma nova vaga

### Teste 5: Status de Candidatura
- Como empresa, altere o status de uma candidatura
- Como aluno, veja a mudança de status

---

**Bons testes! 🚀**
