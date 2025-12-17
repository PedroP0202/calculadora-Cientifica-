| Tema                                     | Calculadora avançada                                                                                       |
|------------------------------------------|------------------------------------------------------------------------------------------------------------|
| Grupo                                    | 06                                                                                                         |
| Participantes                            | Pedro Piedade                                                                                              |
| Funcionalidade baseada em LLM            | A LLM transforma frases do utilizador em expressões matemáticas e resolve operações mais complexas         |
| Classes e responsabilidades              |                                                                                                            |
|                                          | **Main**: Interface principal, Organiza tudo                                                             |
|                                          | **Calculator**: Operações matemáticas básicas (+, -, *, /)                                                |
|                                          | **Operacoes**: Centraliza execução de operações e registro no histórico                                   |
|                                          | **Historico**: Guarda histórico das operações                                                             |
|                                          | **LLMSimples**: Interpreta frases do utilizador, decide se é expressão ou operação complexa, usa LLM      |
|                                          | **LLMInteractionEngine**: Comunicação HTTP com endpoint da LLM customizada                                |
|                                          | **JSONUtils**: Utilitário para extrair respostas do JSON da LLM                                           |
| Observação                               | O código não usa mais Tradutor/LLM antigo, tudo é centralizado em LLMSimples e LLMInteractionEngine       |
