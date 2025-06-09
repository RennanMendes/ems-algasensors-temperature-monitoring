<div align="center">    
    <h1>Algasensors Temperature Monitoring</h1>
</div>

Este é um projeto didático da **AlgaWorks**, desenvolvido no nível 1 do curso "Especialista Microsserviços".  
Tendo como foco principal apresentar os conceitos de **microsserviços** e **mensageria** utilizando o **RabbitMQ**.

🔗 Link do
curso: [Especialista Microsserviços - AlgaWorks](https://lp.algaworks.com/curso-especialista-microsservicos-java-spring-cadastro)  
_Todos os direitos do conteúdo do curso pertencem à AlgaWorks. Este repositório é utilizado apenas para fins
educacionais._

## Visão Geral

Algasensors é uma aplicação projetada para realizar o gerenciamento e monitoramento inteligente de sensores de
temperatura por meio de uma arquitetura moderna baseada em microsserviços.

[Diagrama do projeto](https://whimsical.com/ems-01-07-15-projeto-algasensors-refinando-modelagem-PL457CGTiNJAY3FGqg5oJE)  
[Design Doc: Adoção da Arquitetura de Microsserviços no AlgaSensors](https://www.notion.so/algaworks1/Design-Doc-Ado-o-da-Arquitetura-de-Microsservi-os-no-AlgaSensors-1a5731beea3580489501f870ac7f3c3e?pvs=4)

### 🔧 Principais Componentes

A solução é composta por três microsserviços independentes, cada um responsável por uma capacidade de negócio
específica:

* **Device Management Service**: Gerencia o cadastro, ativação e configuração remota dos sensores.
* **Temperature Processing Service**: Recebe os dados de temperatura em tempo real e os pública para outros serviços.
* **Temperature Monitoring Service**: Analisa, armazena e gera alertas com base nas leituras de temperatura, além de
  permitir consultas ao histórico.

#### 📌 Endpoints Monitoring Service

Responsável pelo armazenamento, análise e alerta de temperatura.

- `GET /api/sensors/{sensorId}/temperatures` - Histórico de temperaturas
- `GET /api/sensors/{sensorId}/alert` - Consulta alerta
- `GET /api/sensors/{sensorId}/monitoring` - Detalhes do monitoramento
- `PUT /api/sensors/{sensorId}/alert` - Configura alerta
- `PUT /api/sensors/{sensorId}/monitoring/enable` - Ativa monitoramento
- `DELETE /api/sensors/{sensorId}/monitoring/enable` - Desativa monitoramento
- `DELETE /api/sensors/{sensorId}/alert` - Remove alerta

#### 📦 Entidades

- SensorMonitoring
   ```json
     {
       "id": "TSID",
       "lastTemperature": 20.4,
       "updatedAt": "OffsetDateTime",
       "enabled": true
     }
   ```
- SensorAlert
   ```json
     {
       "id": "TSID",
       "maxTemperature": 50.0,
       "minTemperature": 10.0
     }
   ```

## Instalação Local

Para começar, você só precisa ir até o repositório principal:

[algasensors-meta](https://github.com/rennanmendes/ems-algasensors-meta)

Lá você vai encontrar todas as instruções pra baixar e rodar o projeto no seu computador.