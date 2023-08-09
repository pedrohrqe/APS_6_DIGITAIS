# Sistema de Acesso com Biometria 🔒👆

Aplicação Java com foco na captura e análise de informações contidas nas impressões digitais, além de realizar autenticação comparativa com um banco de dados simples em MySQL. A aplicação desempenha um papel crucial na verificação de acesso para cada usuário. Utilizando as minúcias extraídas de imagens digitalizadas das impressões digitais, a aplicação busca correspondências no banco de dados. Quando uma correspondência é encontrada, a aplicação concede acesso e apresenta as informações pertinentes associadas ao usuário em questão. Essa abordagem biométrica de autenticação fortalece a segurança e simplifica o processo de gerenciamento de acesso, resultando em um ambiente mais seguro e eficiente.

## Funcionalidades 🚀

- **Autenticação por Impressão Digital:** A aplicação permite capturar informações detalhadas das impressões digitais dos usuários, chamadas de "minúcias", por meio de imagens digitalizadas. Essas minúcias são usadas para comparar com registros no banco de dados MySQL e determinar se o acesso é permitido.

- **Verificação de Acesso:** Com base nas minúcias capturadas, a aplicação verifica a presença do usuário no banco de dados. Se houver correspondência, as informações de acesso são exibidas, indicando o nível de permissão do usuário.

- **Cadastro de Impressão Digital:** A aplicação também oferece a capacidade de cadastrar uma nova impressão digital, sob acesso administrativo. Isso permite a expansão da lista de usuários autorizados.

## Biblioteca de Interpretação das Minúcias 📚

Para a interpretação das minúcias extraídas das impressões digitais, foi utilizada a biblioteca [BiometricSDK](https://sourceforge.net/projects/biometricsdk/). Essa biblioteca oferece recursos avançados de processamento e comparação de características biométricas, o que a torna fundamental para a eficácia da autenticação.

## Requisitos 📝

- Ambiente de desenvolvimento Java de sua preferência

## Aviso ❗

Este projeto foi desenvolvido como parte de uma atividade acadêmica e visa demonstrar o uso de tecnologias de autenticação por impressão digital. O código fonte e o README são fornecidos "como estão", sem garantias de adequação a um propósito específico ou garantia de funcionamento sem erros. Os desenvolvedores não são responsáveis por quaisquer problemas ou consequências decorrentes do uso deste software.

![image](https://user-images.githubusercontent.com/36456794/229405578-2ea5d2ec-b6db-4098-8854-1b3120e03885.png)
