import java.io.*;

public class Computador {

	char 	ativo;
	String 	marca;
	String 	codComp;
	String 	modelo;
	String 	processador;
	int 	quantMemoria;
	int 	tamanhoTela;
	int 	quantEstoque;
	float 	preco;
	int 	quantVendida;
	String 	dtUltimaVenda;
	

	public long localizarComputador(String _codComp) {	
		// metodo para localizar um registro no arquivo em disco
		long posicaoCursorArquivo = 0;
		try { 
			RandomAccessFile arqComp = new RandomAccessFile("COMP.DAT", "rw");
			while (true) {
				posicaoCursorArquivo  = arqComp.getFilePointer();	// posicao do inicio do registro no arquivo
				ativo		 	= arqComp.readChar();
				marca   		= arqComp.readUTF();
				codComp   		= arqComp.readUTF();
				modelo      	= arqComp.readUTF();
				processador 	= arqComp.readUTF();
				quantMemoria 	= arqComp.readInt();
				tamanhoTela		= arqComp.readInt();
				quantEstoque	= arqComp.readInt();
				preco			= arqComp.readFloat();
				quantVendida	= arqComp.readInt();
				dtUltimaVenda	= arqComp.readUTF();

				if ( _codComp.equals(codComp) && ativo == 'S') {
					arqComp.close();
					return posicaoCursorArquivo;
				}
			}
		}catch (EOFException e) {
			return -1; // registro nao foi encontrado
		}catch (IOException e) { 
			System.out.println("Erro na abertura do arquivo  -  programa sera finalizado");
			System.exit(0);
			return -1;
		}
	}

	public void gravarComputador() {	
		// metodo para incluir um novo registro no final do arquivo em disco
		try {
			RandomAccessFile arqComp = new RandomAccessFile("COMP.DAT", "rw");	
			arqComp.seek(arqComp.length());  // posiciona o ponteiro no final do arquivo (EOF)
			arqComp.writeChar(ativo);
			arqComp.writeUTF(marca);
			arqComp.writeUTF(codComp);
			arqComp.writeUTF(modelo);
			arqComp.writeUTF(processador);
			arqComp.writeInt(quantMemoria);
			arqComp.writeInt(tamanhoTela);	
			arqComp.writeInt(quantEstoque);	
			arqComp.writeFloat(preco);	
			arqComp.writeInt(quantVendida);	
			arqComp.writeUTF(dtUltimaVenda);	
			arqComp.close();
			System.out.println("Dados gravados com sucesso !\n");
		}catch (IOException e) { 
			System.out.println("Erro na abertura do arquivo  -  programa sera finalizado");
			System.exit(0);
		}
	}

	public void desativarComputador(long posicao)	{ 
		// metodo para alterar o valor do campo ATIVO para N, tornando assim o registro excluido
		try {
			RandomAccessFile arqComp = new RandomAccessFile("COMP.DAT", "rw");			
			arqComp.seek(posicao);
			arqComp.writeChar('N');   // desativar o registro antigo
			arqComp.close();
		}catch (IOException e) { 
			System.out.println("Erro na abertura do arquivo  -  programa sera finalizado");
			System.exit(0);
		}
	}

	// ***********************   INCLUSAO   *****************************
	public void incluir() {
		String codigoComputador;
		char confirmacao;
		long posicaoRegistro;

		do {
			do {
				Main.leia.nextLine();
				System.out.println("\n ***************  INCLUSAO DE ALUNOS  ***************** ");
				System.out.print("Digite a Matricula do Computador( FIM para encerrar): ");
				codigoComputador = Main.leia.nextLine();
				if (codigoComputador.equals("FIM")) {
					break;
				}
				posicaoRegistro = localizarComputador(codigoComputador);

				if (posicaoRegistro >= 0) {
					System.out.println("Matricula ja cadastrada, digite outro valor\n");
				}
			}while (posicaoRegistro >= 0);

			if (codigoComputador.equals("FIM")) {
				break;
			}

			ativo = 'S';
			codComp = codigoComputador;
			System.out.print("Digite a marca do computador...................: ");
			marca = Main.leia.nextLine();
			System.out.print("Digite o modelo do computador..................: ");
			modelo = Main.leia.nextLine();	    	
			System.out.print("Digite o processador do computador.............: ");
			processador = Main.leia.nextLine();
			System.out.print("Digite a quantidade de mem�ria do computador...: ");
			quantMemoria = Main.leia.nextInt();
			System.out.print("Digite o tamanho da tela.......................: ");
			tamanhoTela = Main.leia.nextInt();
			System.out.print("Digite a quantidade em estoque.................: ");
			quantEstoque = Main.leia.nextInt();
			System.out.print("Digite o valor do computador...................: ");
			preco = Main.leia.nextFloat();
			quantVendida = 0;
			dtUltimaVenda = "";

			do {
				System.out.print("\nConfirma a gravacao dos dados (S/N) ? ");
				confirmacao = Main.leia.next().charAt(0);
				if (confirmacao == 'S') {
					gravarComputador();
				}
			}while (confirmacao != 'S' && confirmacao != 'N');

		}while ( ! codComp.equals("FIM"));	    
	}


	//************************  ALTERACAO  *****************************
	public void alterar() {
		String codigoComputador;
		char confirmacao;
		long posicaoRegistro = 0;
		byte opcao;

		do {
			do {
				Main.leia.nextLine();
				System.out.println("\n ***************  ALTERACAO DE ALUNOS  ***************** ");
				System.out.print("Digite a Matricula do Computador que deseja alterar( FIM para encerrar ): ");
				codigoComputador = Main.leia.nextLine();
				if (codigoComputador.equals("FIM")) {
					break;
				}

				posicaoRegistro = localizarComputador(codigoComputador);
				if (posicaoRegistro >= 0) {
					System.out.println("Matricula nao cadastrada no arquivo, digite outro valor\n");
				}
			}while (posicaoRegistro >= 0);

			if (codigoComputador.equals("FIM")) {
				break;
			}

			ativo = 'S';

			do {
				System.out.println("[ 1 ] Marca do Computador........: " + marca);
				System.out.println("[ 2 ] Modelo do computador ......: " + modelo);
				System.out.println("[ 3 ] Processador................: " + processador);
				System.out.println("[ 4 ] Quantidade de memoria......: " + quantMemoria);
				System.out.println("[ 5 ] Tamanho da tela............: " + tamanhoTela);
				System.out.println("[ 5 ] Quantidade em estoque......: " + quantEstoque);
				System.out.println("[ 6 ] Preco do computador........: " + preco);

				do{
					System.out.println("Digite o numero do campo que deseja alterar (0 para finalizar as alteracoes): ");
					opcao = Main.leia.nextByte();
				}while (opcao < 0 || opcao > 4);

				switch (opcao) {
				case 1:
					Main.leia.nextLine();
					System.out.print  ("Digite a NOVA MARCA do Computador..................: ");
					marca = Main.leia.nextLine();
					break;
				case 2: 
					Main.leia.nextLine();
					System.out.print  ("Digite o NOVO MODELO do computador.................: ");
					modelo = Main.leia.nextLine();
					break;
				case 3:
					System.out.print  ("Digite o NOVO PROCESSADOR do computador............: ");
					processador = Main.leia.nextLine();
					break;
				case 4: 
					System.out.print  ("Digite a NOVA quantidade de memoria do Computador..: ");
					quantMemoria = Main.leia.nextInt();
					break;
				case 5: 
					System.out.print  ("Digite o NOVO tamanho de tela......................: ");
					tamanhoTela = Main.leia.nextInt();
					break;
				case 6: 
					System.out.print  ("Digite o NOVO preco do Computador..................: ");
					preco = Main.leia.nextFloat();
					break;
				}
				quantVendida = 0;
				dtUltimaVenda = "";
				System.out.println();
			}while (opcao != 0);  		

			do {
				System.out.print("\nConfirma a alteracao dos dados (S/N) ? ");
				confirmacao = Main.leia.next().charAt(0);
				if (confirmacao == 'S') {
					desativarComputador(posicaoRegistro);
					gravarComputador();
					System.out.println("Dados gravados com sucesso !\n");
				}
			}while (confirmacao != 'S' && confirmacao != 'N');

		}while ( ! codComp.equals("FIM"));
	}


	//************************  EXCLUSAO  *****************************
	public void excluir() {
		String codigoComputador;
		char confirmacao;
		long posicaoRegistro = 0;

		do {
			do {
				Main.leia.nextLine();
				System.out.println(" ***************  EXCLUSAO DE ALUNOS  ***************** ");
				System.out.print("Digite a Matricula do Computador que deseja excluir ( FIM para encerrar ): ");
				codigoComputador = Main.leia.nextLine();
				if (codigoComputador.equals("FIM")) {
					break;
				}

				posicaoRegistro = localizarComputador(codigoComputador);
				if (posicaoRegistro >= 0) {
					System.out.println("Matricula nao cadastrada no arquivo, digite outro valor\n");
				}
			}while (posicaoRegistro >= 0);

			if (codigoComputador.equals("FIM")) {
				System.out.println("\n ************  PROGRAMA ENCERRADO  ************** \n");
				break;
			}

			System.out.println("Marca do Computador........: " + marca);
			System.out.println("Modelo do computador ......: " + modelo);
			System.out.println("Processador................: " + processador);
			System.out.println("Quantidade de memoria......: " + quantMemoria);
			System.out.println("Tamanho da tela............: " + tamanhoTela);
			System.out.println("Quantidade em estoque......: " + quantEstoque);
			System.out.println("Preco do computador........: " + preco);
			System.out.println();

			do {
				System.out.print("\nConfirma a exclusao deste aluno (S/N) ? ");
				confirmacao = Main.leia.next().charAt(0);
				if (confirmacao == 'S') {
					desativarComputador(posicaoRegistro);
				}
			}while (confirmacao != 'S' && confirmacao != 'N');

		}while ( ! codComp.equals("FIM"));
	}

	//************************  CONSULTA  *****************************
	public void consultar() 	{
		RandomAccessFile arqComp;
		byte opcao;
		String codigoComputador;
		long posicaoRegistro;

		do {
			do {
				System.out.println(" ***************  CONSULTA DE ALUNOS  ***************** ");
				System.out.println(" [1] CONSULTAR APENAS 1 COMPUTADOR ");
				System.out.println(" [2] LISTA DE TODOS OS COMPUTADORES ");
				System.out.println(" [3] LISTA DE TODOS OS COMPUTADORES VENDIDOS");
				System.out.println(" [0] SAIR");
				System.out.print("\nDigite a opcao desejada: ");
				opcao = Main.leia.nextByte();
				if (opcao < 0 || opcao > 2) {
					System.out.println("opcao Invalida, digite novamente.\n");
				}
			}while (opcao < 0 || opcao > 2);

			switch (opcao) {
			case 0:
				System.out.println("\n ************  PROGRAMA ENCERRADO  ************** \n");
				break;

			case 1:  // consulta de uma unica matricula
				Main.leia.nextLine();  // limpa buffer de memoria
				System.out.print("Digite a Matriocula do Computador: ");
				codigoComputador = Main.leia.nextLine();

				posicaoRegistro = localizarComputador(codigoComputador);
				if (posicaoRegistro >= 0) {
					System.out.println("Matricula nao cadastrada no arquivo \n");
				} else {
					imprimirCabecalho();
					imprimirComputador();
					System.out.println("\n FIM DE RELATORIO - ENTER para continuar...\n");
					Main.leia.nextLine();
				}

				break;

			case 2:  // imprime todos os alunos
				try { 
					arqComp = new RandomAccessFile("COMP.DAT" , "rw");
					imprimirCabecalho();
					while (true) {
						ativo		 	= arqComp.readChar();
						marca   		= arqComp.readUTF();
						codComp   		= arqComp.readUTF();
						modelo      	= arqComp.readUTF();
						processador 	= arqComp.readUTF();
						quantMemoria 	= arqComp.readInt();
						tamanhoTela		= arqComp.readInt();
						quantEstoque	= arqComp.readInt();
						preco			= arqComp.readFloat();
						quantVendida	= arqComp.readInt();
						dtUltimaVenda	= arqComp.readUTF();
						if ( ativo == 'S') {
							imprimirComputador();
						}
					}
					//    arqAluno.close();
				} catch (EOFException e) {
					System.out.println("\n FIM DE RELATORIO - ENTER para continuar...\n");
					Main.leia.nextLine();
					codigoComputador = Main.leia.nextLine();
				} catch (IOException e) { 
					System.out.println("Erro na abertura do arquivo - programa sera finalizado");
					System.exit(0);
				}
				break;

			case 3:  // imprime alunos do sexo desejado
//				do {
//					System.out.print("Digite o Sexo desejado (M/F): ");
//					sexoAux = Main.leia.next().charAt(0);
//					if (sexoAux != 'F' && sexoAux != 'M') {
//						System.out.println("Sexo Invalido, digite M ou F");
//					}
//				}while (sexoAux != 'F' && sexoAux != 'M');

				try { 
					arqComp = new RandomAccessFile("COMP.DAT", "rw");
					imprimirCabecalho();
					while (true) {
						ativo		 	= arqComp.readChar();
						marca   		= arqComp.readUTF();
						codComp   		= arqComp.readUTF();
						modelo      	= arqComp.readUTF();
						processador 	= arqComp.readUTF();
						quantMemoria 	= arqComp.readInt();
						tamanhoTela		= arqComp.readInt();
						quantEstoque	= arqComp.readInt();
						preco			= arqComp.readFloat();
						quantVendida	= arqComp.readInt();
						dtUltimaVenda	= arqComp.readUTF();
						if ( quantVendida > 0 && ativo == 'S') {
							imprimirComputador();
						}
					}
				} catch (EOFException e) {
					System.out.println("\n FIM DE RELATORIO - ENTER para continuar...\n");
					Main.leia.nextLine();
					codigoComputador = Main.leia.nextLine();
				} catch (IOException e) { 
					System.out.println("Erro na abertura do arquivo - programa sera finalizado");
					System.exit(0);
				}

			}	

		} while ( opcao != 0 );
	}

	public void imprimirCabecalho () {
		System.out.println("---MARCA---  ---CODIGO---  --MODELO--  -PROCESSADOR-  -QTD MEMORIA- -TAMANHO TELA-  --QTD ESTOQUE-- --PRECO-- -QTD VENDIDA- -ULTIMA VENDA-");
	}

	public void imprimirComputador () {
		System.out.println(	formatarString(marca, 14 ) + "  " +
				formatarString(codComp , 11) + "  " + 
				formatarString(modelo , 12) + "  " + 
				formatarString(processador , 8) + "  " +
				formatarString( String.valueOf(quantMemoria) , 13 ) + "  " +
				formatarString( String.valueOf(tamanhoTela) , 13 ) + "  " +
				formatarString( String.valueOf(quantEstoque) , 13 ) + "  " +
				formatarString( String.valueOf(preco) , 13 ) + "  " +
				formatarString( String.valueOf(quantVendida) , 13 ) + "  " +
				formatarString(dtUltimaVenda, 13 )   ); 
	}

	public static String formatarString (String texto, int tamanho) {	
		// retorna uma string com o numero de caracteres passado como parametro em TAMANHO
		if (texto.length() > tamanho) {
			texto = texto.substring(0,tamanho);
		}else{
			while (texto.length() < tamanho) {
				texto = texto + " ";
			}
		}
		return texto;
	}
}
