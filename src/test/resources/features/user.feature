#language: pt

Funcionalidade: CRUD de usuarios

  Contexto:
    Dado que utilizem o CRUD de gerenciamento de usuarios

  Esquema do Cenario: Pesquisa informacoes de um unico usuario utilizando um id existente
    Quando utilizamos o get passando o id de usuario existente '<id>'
    Entao retorna status 200 com as informacoes do usuario
    Exemplos:
      | id |
      | 1  |
      | 2  |
      | 3  |

  Esquema do Cenario: Pesquisa informacoes de um unico usuario utilizando id inexistente
    Quando utilizamos o get passando um id de usuario inexistente '<id>'
    Entao retorna status 404 com valor de resposta vazio
    Exemplos:
      | id |
      | 999999999 |

  Esquema do Cenario: Criar um usuario com dados validos
    Quando utilizamos o post para criar um usuario com o nome '<name>' e funcao '<job>'
    Entao retorna status 201 com o novo id e data de criacao
    Exemplos:
      | name  | job |
      | Sonic | Hedgehog |
      | Mario | Plumber  |

    ##ponto de melhoria sobre cenario abaixo:
    # Usuário não deveria ser criado com status 201,
    # mas sim retornar o HttpStatus 400 (Bad Request) sem concluir a criação
  Esquema do Cenario: Criar um usuario com dados incompletos
    Quando utilizamos o post para criar um usuario com o nome '<name>' e funcao '<job>' com um dos dados faltando
    Entao retorna status 201 com valor com usuario faltando dados
    Exemplos:
      | name  | job |
      |  | Hedgehog |
      | Mario |   |

    Esquema do Cenario: Atualizar um usuario com dados validos
      Quando utilizamos o put para atualizar o usuario com '<name>' e funcao '<job>'
      Entao retorna status 200 com o usuario com dados atualizados
      Exemplos:
        | name  | job |
        | Legolas | Archer  |

    Esquema do Cenario: Deletar um usuario
      Quando utilizamos o delete para deletar um usuario com o id '<id>'
      Entao retorna status 204
      Exemplos:
        | id |
        | 1 |
        | 2 |
        | 3 |