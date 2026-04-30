# 🐾 UniPatas

Sistema de gerenciamento para adoção de animais desenvolvido como projeto acadêmico para a disciplina de **AEDS III** (Algoritmos e Estruturas de Dados III).

## 📚 Sobre o Projeto
O foco principal deste sistema é a aplicação de conceitos de **persistência de dados em memória secundária**. Diferente de bancos de dados convencionais, o UniPatas utiliza a classe `RandomAccessFile` do Java para manipulação direta de arquivos binários, simulando o comportamento de um banco de dados de baixo nível.

## 🏗️ Arquitetura (MVC)
O projeto segue o padrão de arquitetura **Model-View-Controller**:
* **Model:** Representação das entidades.
* **DAO (Data Access Object):** Camada de persistência responsável pelo CRUD no arquivo binário.
* **Controller:** Lógica de negócio e ponte entre a interface e os dados.
* **View:** Interface gráfica desenvolvida em **JavaFX**.

## 💾 Persistência
* **Formato:** Arquivo binário (`.db`).
* **Técnica:** Acesso aleatório via `RandomAccessFile`.

## 🚀 Tecnologias Utilizadas
* **Linguagem:** Java 21
* **Interface Gráfica:** JavaFX
* **Gerenciador de Dependências:** Maven

---

## ▶️ Como Executar o Projeto

### 🛠️ Pré-requisitos
Antes de começar, você precisará ter instalado em sua máquina:
* **JDK 17 ou 21**
* **Maven** instalado e configurado no PATH.

---

### 🐧 Linux (Ubuntu/Debian ou WSL)

Atualizar repositórios e instalar dependências:
sudo apt update
sudo apt install openjdk-21-jdk maven
Entrar na pasta do projeto:
cd caminho/para/UniPatas
Executar a aplicação:
mvn org.openjfx:javafx-maven-plugin:0.0.8:run -Djavafx.mainClass="br.com.unipatas.Launcher"

### 🪟 Windows (PowerShell)
Verificar se Java e Maven estão instalados:
java -version
mvn -version
Executar a aplicação:
mvn org.openjfx:javafx-maven-plugin:0.0.8:run "-Djavafx.mainClass=br.com.unipatas.Launcher"

### 🍎 macOS (Terminal)
Instalar dependências com Homebrew:
brew install openjdk@21 maven
Entrar na pasta do projeto:
cd caminho/para/UniPatas
Executar a aplicação:
mvn org.openjfx:javafx-maven-plugin:0.0.8:run -Djavafx.mainClass="br.com.unipatas.Launcher"

## 👩‍💻 Desenvolvedores
- Any Gabriela
- Guilherme Pinheiro
- Henrique Gonçalves
- Lucca Sander
