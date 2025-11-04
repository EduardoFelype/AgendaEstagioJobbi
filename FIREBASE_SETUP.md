# 🔥 Guia de Configuração do Firebase para Jobbi

Este guia detalha passo a passo como configurar o Firebase para o aplicativo Jobbi.

## 📋 Índice

1. [Criar Projeto no Firebase](#1-criar-projeto-no-firebase)
2. [Adicionar App Android](#2-adicionar-app-android)
3. [Configurar Authentication](#3-configurar-authentication)
4. [Configurar Firestore Database](#4-configurar-firestore-database)
5. [Regras de Segurança](#5-regras-de-segurança)
6. [Testar Conexão](#6-testar-conexão)

---

## 1. Criar Projeto no Firebase

1. Acesse [Firebase Console](https://console.firebase.google.com/)
2. Clique em **"Adicionar projeto"** ou **"Create a project"**
3. Digite o nome do projeto: `Jobbi` (ou o nome que preferir)
4. (Opcional) Desative o Google Analytics se não for usar
5. Clique em **"Criar projeto"**
6. Aguarde a criação do projeto

---

## 2. Adicionar App Android

1. No painel do projeto, clique no ícone **Android** (</>) para adicionar um app
2. Preencha os campos:
   - **Package name**: `com.agenda.estagio` ⚠️ **IMPORTANTE: Use exatamente este nome**
   - **App nickname**: `Jobbi` (opcional)
   - **SHA-1**: Deixe em branco por enquanto (necessário apenas para Google Sign-In)
3. Clique em **"Registrar app"**
4. **Baixe o arquivo `google-services.json`**
5. Mova o arquivo para a pasta `app/` do projeto Android:
   ```
   agenda_estagio_project/
   └── app/
       └── google-services.json  ← Coloque aqui
   ```
6. Clique em **"Próximo"** e depois **"Continuar no console"**

---

## 3. Configurar Authentication

### Ativar Email/Password:

1. No menu lateral, clique em **"Authentication"**
2. Clique na aba **"Sign-in method"**
3. Clique em **"Email/Password"**
4. Ative a opção **"Email/Password"** (primeira opção)
5. **NÃO ative** a opção "Email link (passwordless sign-in)"
6. Clique em **"Salvar"**

### (Opcional) Configurar domínios autorizados:

1. Na aba **"Settings"** > **"Authorized domains"**
2. Adicione domínios se necessário (para web)

---

## 4. Configurar Firestore Database

### Criar banco de dados:

1. No menu lateral, clique em **"Firestore Database"**
2. Clique em **"Criar banco de dados"**
3. Escolha o modo:
   - **Modo de teste** (recomendado para desenvolvimento)
   - **Modo de produção** (para apps em produção)
4. Selecione a localização do banco:
   - Escolha a região mais próxima (ex: `southamerica-east1` para Brasil)
5. Clique em **"Ativar"**

### Estrutura de coleções:

O Firestore criará as coleções automaticamente quando você adicionar dados. As coleções serão:

- `users` - Dados de alunos e empresas
- `vagas` - Vagas publicadas
- `candidaturas` - Candidaturas dos alunos

---

## 5. Regras de Segurança

### Para desenvolvimento (modo teste):

As regras padrão do modo teste são:

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /{document=**} {
      allow read, write: if request.time < timestamp.date(2024, 12, 31);
    }
  }
}
```

⚠️ **ATENÇÃO**: Estas regras expiram na data especificada e permitem acesso total!

### Para produção (recomendado):

Substitua pelas seguintes regras de segurança:

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    
    // Regras para usuários
    match /users/{userId} {
      // Permitir leitura do próprio perfil
      allow read: if request.auth != null && request.auth.uid == userId;
      
      // Permitir criação apenas se o ID corresponder ao usuário autenticado
      allow create: if request.auth != null && request.auth.uid == userId;
      
      // Permitir atualização apenas do próprio perfil
      allow update: if request.auth != null && request.auth.uid == userId;
      
      // Não permitir deleção
      allow delete: if false;
    }
    
    // Regras para vagas
    match /vagas/{vagaId} {
      // Qualquer usuário autenticado pode ler vagas
      allow read: if request.auth != null;
      
      // Apenas empresas podem criar vagas
      allow create: if request.auth != null && 
                       get(/databases/$(database)/documents/users/$(request.auth.uid)).data.tipo == 'EMPRESA';
      
      // Apenas a empresa dona pode atualizar/deletar
      allow update, delete: if request.auth != null && 
                               resource.data.empresaId == request.auth.uid;
    }
    
    // Regras para candidaturas
    match /candidaturas/{candidaturaId} {
      // Aluno pode ler suas próprias candidaturas
      // Empresa pode ler candidaturas de suas vagas
      allow read: if request.auth != null && 
                     (resource.data.alunoId == request.auth.uid || 
                      resource.data.empresaId == request.auth.uid);
      
      // Apenas alunos podem criar candidaturas
      allow create: if request.auth != null && 
                       get(/databases/$(database)/documents/users/$(request.auth.uid)).data.tipo == 'ALUNO';
      
      // Aluno pode atualizar/cancelar suas candidaturas
      // Empresa pode atualizar status das candidaturas de suas vagas
      allow update: if request.auth != null && 
                       (resource.data.alunoId == request.auth.uid || 
                        resource.data.empresaId == request.auth.uid);
      
      // Apenas o aluno pode deletar sua candidatura
      allow delete: if request.auth != null && 
                       resource.data.alunoId == request.auth.uid;
    }
  }
}
```

### Como aplicar as regras:

1. No Firestore Database, clique na aba **"Regras"**
2. Cole o código acima
3. Clique em **"Publicar"**

---

## 6. Testar Conexão

### No Android Studio:

1. Abra o projeto no Android Studio
2. Verifique se o arquivo `google-services.json` está em `app/`
3. Sincronize o Gradle: **File** > **Sync Project with Gradle Files**
4. Execute o app em um emulador ou dispositivo
5. Tente criar uma conta de aluno ou empresa

### Verificar no Firebase Console:

1. Vá em **Authentication** > **Users**
   - Você deve ver os usuários criados
2. Vá em **Firestore Database** > **Data**
   - Você deve ver as coleções `users`, `vagas`, `candidaturas` sendo criadas

---

## 🔍 Solução de Problemas

### Erro: "google-services.json not found"

- Certifique-se de que o arquivo está em `app/google-services.json`
- Sincronize o Gradle novamente

### Erro: "FirebaseApp initialization unsuccessful"

- Verifique se o package name está correto: `com.agenda.estagio`
- Baixe novamente o `google-services.json` do Firebase Console

### Erro: "Permission denied" no Firestore

- Verifique as regras de segurança no Firebase Console
- Para testes, use as regras do modo teste
- Certifique-se de que o usuário está autenticado

### Erro: "Network error" ou "Timeout"

- Verifique sua conexão com a internet
- Certifique-se de que o Firebase está configurado corretamente
- Tente limpar o cache do app: **Settings** > **Apps** > **Jobbi** > **Clear Cache**

---

## 📚 Recursos Adicionais

- [Documentação oficial do Firebase](https://firebase.google.com/docs)
- [Firebase Authentication](https://firebase.google.com/docs/auth)
- [Cloud Firestore](https://firebase.google.com/docs/firestore)
- [Regras de Segurança](https://firebase.google.com/docs/firestore/security/get-started)

---

## ✅ Checklist de Configuração

- [ ] Projeto criado no Firebase Console
- [ ] App Android adicionado com package name correto
- [ ] Arquivo `google-services.json` baixado e colocado em `app/`
- [ ] Authentication habilitado (Email/Password)
- [ ] Firestore Database criado
- [ ] Regras de segurança configuradas
- [ ] Projeto sincronizado no Android Studio
- [ ] App testado e funcionando

---

**Pronto! Seu Firebase está configurado e pronto para uso! 🎉**
