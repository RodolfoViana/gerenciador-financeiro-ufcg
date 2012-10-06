package com.name.gfp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.widget.TableRow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

public class GFPActivity extends Activity {
	/** Called when the activity is first created. */

	GerenciadorFinanceiro gerenciador = new GerenciadorFinanceiro();
	int IDlinha = 0;

//	TableLayout tl = (TableLayout) findViewById(R.main.tabela);
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		 telaPrincipal();
		
		// TableRow tr1 = new TableRow(this);
		// tr1.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
		// LayoutParams.WRAP_CONTENT));
		//
		//
		// TextView t4 = new TextView(this);
		// t4.setText("Descricao1");
		// TextView t5 = new TextView(this);
		// t5.setText("Categoria1");
		// TextView t6 = new TextView(this);
		// t6.setText("   Valor1");
		//
		// tr1.addView(t4);
		// tr1.addView(t5);
		// tr1.addView(t6);
		//
		// TableRow tr2 = new TableRow(this);
		// tr2.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
		// LayoutParams.WRAP_CONTENT));
		//
		//
		// TextView t7 = new TextView(this);
		// t7.setText("Descricao2");
		// TextView t8 = new TextView(this);
		// t8.setText("Categoria2");
		// TextView t9 = new TextView(this);
		// t9.setText("   Valor2");
		//
		// tr2.addView(t7);
		// tr2.addView(t8);
		// tr2.addView(t9);
		//
		// TableRow tr3 = new TableRow(this);
		//
		// tr3.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
		// LayoutParams.WRAP_CONTENT));
		//
		// TextView t10 = new TextView(this);
		// t10.setText("Descricao3");
		// TextView t11 = new TextView(this);
		// t11.setText("Categoria3");
		// TextView t12 = new TextView(this);
		// t12.setText("   Valor3");
		//
		// tr3.addView(t10);
		// tr3.addView(t11);
		// tr3.addView(t12);
		//
		// tl.addView(tr1, new
		// TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
		// LayoutParams.WRAP_CONTENT));
		// tl.addView(tr2, new
		// TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
		// LayoutParams.WRAP_CONTENT));
		// tl.addView(tr3, new
		// TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
		// LayoutParams.WRAP_CONTENT));

	}
	
	private void povoarTabela(int numeroLinhas, int mes, TableLayout tl){
//		tl.removeAllViews();
		
		
		
		if (numeroLinhas != 0){
			for(int i = 0 ; i<numeroLinhas;i++){
//				if(this.gerenciador.getListaDeTransacoes().get(i).getMes() == mes){
					TableRow linha = new TableRow(this);
					linha.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
							 LayoutParams.WRAP_CONTENT));
						
					TextView tipo = new TextView(this);
					TextView data = new TextView(this);
					TextView categoria=new TextView(this);
					TextView valor=new TextView(this);
					
					data.setText(this.gerenciador.getListaDeTransacoes().get(i).getData());
					categoria.setText(this.gerenciador.getListaDeTransacoes().get(i).getCategoria());
					valor.setText(String.valueOf(this.gerenciador.getListaDeTransacoes().get(i).getValor()));
					tipo.setText(this.gerenciador.getListaDeTransacoes().get(i).getTipo());
					
					linha.addView(tipo);
					linha.addView(data);
					linha.addView(categoria);
					linha.addView(valor);
					tl.addView(linha, new TableLayout.LayoutParams(
							LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
					
					IDlinha = this.gerenciador.getListaDeTransacoes().get(i).getID();
					
					linha.setOnClickListener(new View.OnClickListener() {
						
						
						public void onClick(View v) {
							
							showDialog(0);
						}
					});
				}
//			}
		}
	}
	
	private void editar(){
		boolean tipo = gerenciador.getTransacao(IDlinha).isDespesa();
		
		String data = gerenciador.getTransacao(IDlinha).getData();
		double valor = gerenciador.getTransacao(IDlinha).getValor();
		String descricao = gerenciador.getTransacao(IDlinha).getDescricao();
		String categoria = gerenciador.getTransacao(IDlinha).getCategoria();
		
		if (tipo){			
			telaEditarDespesa(data, categoria, valor, descricao);
		}else {
			telaEditarReceita(data, categoria, valor, descricao);
		}
			
		
	}
	
	@Override
    protected Dialog onCreateDialog(int id) {

         switch (id) {
         case 0:

              //Primeiro precisamos criar um inflater que adapte o conteudo do xml para o AlertDialog
              LayoutInflater factory = LayoutInflater.from(this);
              final View textEntryView = factory.inflate(R.layout.editardeletar, null); //passamos o XML criado
              return new AlertDialog.Builder(GFPActivity.this)
                   .setTitle("O que deseja ???").setView(textEntryView)
                   .setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        	editar();
                        	

                   }
              }).setNegativeButton("Deletar", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int whichButton) {
                	   gerenciador.excluirTransacao(IDlinha);
                	   imprimirMensagem("Deletado", "Sucesso");
                	   telaPrincipal();
                	  dialog.cancel();
                        //onClick acionado caso o usu�rio aperte o bot�o "Cancelar"
//                        GFPActivity.this.finish();
                   }
              }).create(); //por fim criamos o AlertDialog depois de todo constru�do (t�tulo, layout, bot�es e a��es)

         }

         //Se o valor passado pelo par�metro n�o for o da constante SHOW_DIALOG retornamos null
         return null;
    }
	
	
	private int getMes(String mesSelect){
		String[] listaMeses = { "Janeiro", "Fevereiro", "Mar�o", "Abril",
				"Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro",
				"Novembro", "Dezembro" };

		for (int i = 0; i < listaMeses.length; i++) {
			if (listaMeses[i].equals(mesSelect)) {
				return i+1;
			}
		}return -1;
	}
	
	public void telaPrincipal(){
		setContentView(R.layout.main);
		final TableLayout tl = (TableLayout) findViewById(R.main.tabela);

		/* Find Tablelayout defined in main.xml */
		
		final TextView t1 = new TextView(this);
		t1.setText("        Tipo   ");
		TextView t2 = new TextView(this);
		t2.setText("      Data   ");
		TextView t3 = new TextView(this);
		t3.setText("       Categoria");
		TextView t4 = new TextView(this);
		t4.setText("         Valor");

		TableRow tr = (TableRow) findViewById(R.main.linha1);
//		tr.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
//				 LayoutParams.WRAP_CONTENT));
		tr.addView(t1);
		tr.addView(t2);
		tr.addView(t3);
		tr.addView(t4);
		
		// Spinner

		Spinner sp = (Spinner) findViewById(R.main.spinner1);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.itens, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp.setAdapter(adapter);
		
//		sp.setOnItemSelectedListener();
		
		//Selcao de mes...
		final int mes = getMes(sp.getSelectedItem().toString());
		final int numeroLinhas = gerenciador.getListaDeTransacoes().size();
		povoarTabela(numeroLinhas, mes,  tl);
		
		
				
//				sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
//			        public void onItemSelected(AdapterView<?> arg0, View arg1,
//				                int position, long id) {
//				        	povoarTabela(numeroLinhas, mes, tl);			 
//					 }
//					public void onNothingSelected(AdapterView<?> arg0) {}
//				});
		
	
//		if (numeroLinhas != 0){
//			for(int i = 0 ; i<numeroLinhas;i++){
//				if(this.gerenciador.getListaDeTransacoes().get(i).getMes() == mes){
//					TableRow linha = new TableRow(this);
//					linha.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
//							 LayoutParams.WRAP_CONTENT));
//						
//					TextView tipo = new TextView(this);
//					TextView data = new TextView(this);
//					TextView categoria=new TextView(this);
//					TextView valor=new TextView(this);
//					
//					data.setText(this.gerenciador.getListaDeTransacoes().get(i).getData());
//					categoria.setText(this.gerenciador.getListaDeTransacoes().get(i).getCategoria());
//					valor.setText(String.valueOf(this.gerenciador.getListaDeTransacoes().get(i).getValor()));
//					tipo.setText(this.gerenciador.getListaDeTransacoes().get(i).getTipo());
//					
//					linha.addView(tipo);
//					linha.addView(data);
//					linha.addView(categoria);
//					linha.addView(valor);
//					tl.addView(linha, new TableLayout.LayoutParams(
//							LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//					
//					IDlinha = this.gerenciador.getListaDeTransacoes().get(i).getID();
//					
//					linha.setOnClickListener(new View.OnClickListener() {
//						
//						
//						public void onClick(View v) {
//							
//							showDialog(0);
//						}
//					});
//				}
//			}
//		}

		//POVOANDO A TABELA...
		
		
//		 TableRow tr1 = new TableRow(this);
//				 tr1.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
//				 LayoutParams.WRAP_CONTENT));
//				
//				
//				 TextView t4 = new TextView(this);
//				 t4.setText("Descricao1");
//				 TextView t5 = new TextView(this);
//				 t5.setText("Categoria1");
//				 TextView t6 = new TextView(this);
//				 t6.setText("   Valor1");
//				
//				 tr1.addView(t4);
//				 tr1.addView(t5);
//				 tr1.addView(t6);
//				
//				 TableRow tr2 = new TableRow(this);
//				 tr2.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
//				 LayoutParams.WRAP_CONTENT));
//				
//				
//				 TextView t7 = new TextView(this);
//				 t7.setText("Descricao2");
//				 TextView t8 = new TextView(this);
//				 t8.setText("Categoria2");
//				 TextView t9 = new TextView(this);
//				 t9.setText("   Valor2");
//				
//				 tr2.addView(t7);
//				 tr2.addView(t8);
//				 tr2.addView(t9);
//				
//				 TableRow tr3 = new TableRow(this);
//				
//				 tr3.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
//				 LayoutParams.WRAP_CONTENT));
//				
//				 TextView t10 = new TextView(this);
//				 t10.setText("Descricao3");
//				 TextView t11 = new TextView(this);
//				 t11.setText("Categoria3");
//				 TextView t12 = new TextView(this);
//				 t12.setText("   Valor3");
//				
//				 tr3.addView(t10);
//				 tr3.addView(t11);
//				 tr3.addView(t12);
//				
//				 tl.addView(tr1, new
//				 TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
//				 LayoutParams.WRAP_CONTENT));
//				 tl.addView(tr2, new
//				 TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
//				 LayoutParams.WRAP_CONTENT));
//				 tl.addView(tr3, new
//				 TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
//				 LayoutParams.WRAP_CONTENT));
		
		
		
		
		

		Button adicionarReceita = (Button) findViewById(R.main.adicionarReceita);

		adicionarReceita.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				telaAdicionarReceita();
			}
		});
		
		 Button adicionarDespesa = (Button) findViewById
				 (R.main.adicionarDespesa);

		adicionarDespesa.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				telaAdicionarDespesa();
			}
		});
	}

	private int positionCategoria(String categoria){		
		if (categoria.equals("Alimentacao")){
			return 0;
		} 
		if (categoria.equals("Lazer")){
			return 1;
		}
		if (categoria.equals("Moradia")){
			return 2;
		}
		if (categoria.equals("Salario")){
			return 3;
		}
		if (categoria.equals("Saude")){
			return 4;
		}
		if (categoria.equals("Transporte")){
			return 5;
		}
		if (categoria.equals("Outros")){
			return 6;
		}
		return 0;
	}
	
	public void telaEditarReceita(String data, String categoria, double valor, String descricao) {
		setContentView(R.layout.adicionar);

		Spinner spinnerTipo = (Spinner) findViewById(R.adicionar.spinnerTipo);
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
				this, R.array.categorias, android.R.layout.simple_spinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerTipo.setAdapter(adapter2);

		Button btEditar = (Button) findViewById(R.adicionar.btAdd);
		
		((EditText) findViewById(R.adicionar.editTextData)).setText(data);
		((EditText) findViewById(R.adicionar.valor)).setText(new Double(valor).toString());
		((EditText) findViewById(R.adicionar.editTextDescricao)).setText(descricao);
		((Spinner) findViewById(R.adicionar.spinnerTipo)).setSelection(positionCategoria(categoria));


		btEditar.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				String descricao = ((EditText) findViewById(R.adicionar.editTextDescricao))
						.getText().toString();
				Double valor = Double
						.parseDouble(((EditText) findViewById(R.adicionar.valor))
								.getText().toString());
				String data = ((EditText) findViewById(R.adicionar.editTextData))
						.getText().toString();
				String categoria = ((Spinner) findViewById(R.adicionar.spinnerTipo))
						.getSelectedItem().toString();

					try {
						gerenciador.adicionarReceita(data, valor, categoria, descricao);
						gerenciador.excluirTransacao(IDlinha);
						imprimirMensagem(
								"Transacao editada",
								"Sucesso");
						telaPrincipal();
						
					} catch (Exception e) {
						imprimirMensagem(e.getMessage(), "Ops...");
					}

			}
		});

	}
	
	public void telaEditarDespesa(String data, String categoria, double valor, String descricao){
		setContentView(R.layout.adicionardespesa);
		
		Spinner spinnerCategoria = (Spinner) findViewById(R.adicionarDespesa.spinnerCategoria);
		ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
				this, R.array.categorias, android.R.layout.simple_spinner_item);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerCategoria.setAdapter(adapter1);

		Button editar = (Button) findViewById(R.adicionarDespesa.btAdd);
		
		((EditText) findViewById(R.adicionarDespesa.editTextData)).setText(data);
		((EditText) findViewById(R.adicionarDespesa.valor)).setText(new Double(valor).toString());
		((EditText) findViewById(R.adicionarDespesa.editTextDescricao)).setText(descricao);
		((Spinner) findViewById(R.adicionarDespesa.spinnerCategoria)).setSelection(positionCategoria(categoria));

		editar.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {

				String data = ((EditText) findViewById(R.adicionarDespesa.editTextData))
						.getText().toString();
				double valor = Double
						.parseDouble(((EditText) findViewById(R.adicionarDespesa.valor))
								.getText().toString());
				String descricao = ((EditText) findViewById(R.adicionarDespesa.editTextDescricao))
						.getText().toString();
			String categoria = ((Spinner) findViewById(R.adicionarDespesa.spinnerCategoria))
						.getSelectedItem().toString();
				
					try {
						gerenciador.adicionarDespesa(data, valor, categoria, descricao);
						gerenciador.excluirTransacao(IDlinha);
						imprimirMensagem(
								"Transa��o editada",
								"Sucesso");
						telaPrincipal();
						
					} catch (Exception e) {
						imprimirMensagem(e.getMessage(), "Ops...");
					}

			}
		});
	}
	
	public void telaAdicionarDespesa() {
		setContentView(R.layout.adicionardespesa);

//		Spinner spinnerTipo = (Spinner) findViewById(R.adicionarDespesa.spinnerTipo);
//		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
//				this, R.array.tipo, android.R.layout.simple_spinner_item);
//		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		spinnerTipo.setAdapter(adapter2);

		Spinner spinnerCategoria = (Spinner) findViewById(R.adicionarDespesa.spinnerCategoria);
		ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
				this, R.array.categorias, android.R.layout.simple_spinner_item);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerCategoria.setAdapter(adapter1);

		Button btAdd = (Button) findViewById(R.adicionarDespesa.btAdd);

		btAdd.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				String data = ((EditText) findViewById(R.adicionarDespesa.editTextData))
						.getText().toString();
				double valor = Double
						.parseDouble(((EditText) findViewById(R.adicionarDespesa.valor))
								.getText().toString());
				String descricao = ((EditText) findViewById(R.adicionarDespesa.editTextDescricao))
						.getText().toString();
//				String tipo = ((Spinner) findViewById(R.adicionarDespesa.spinnerTipo))
//						.getSelectedItem().toString();
//				int numeroDeParcelas = Integer
//						.parseInt(((EditText) findViewById(R.adicionarDespesa.editTextParcelas))
//								.getText().toString());
				String categoria = ((Spinner) findViewById(R.adicionarDespesa.spinnerCategoria))
						.getSelectedItem().toString();
				

//				if (tipo.toLowerCase().equals("fixo")) {
					try {
						gerenciador.adicionarDespesa(data, valor, categoria, descricao);
						imprimirMensagem(
								"Transacao Adicionada com Sucesso !!!",
								"Sucesso");
						telaPrincipal();
						
					} catch (Exception e) {
						imprimirMensagem(e.getMessage(), "Ops...");
					}
//				} else if (tipo.toLowerCase().equals("mensalmente")) {
//					try {
//						gerenciador.adicionarDespesa(data, valor, descricao,
//								Tipo.FIXO, categoria, numeroDeParcelas);
//						imprimirMensagem(
//								"Transacao Adicionada com Sucesso !!!",
//								"Ops...");
//						telaPrincipal();
//						
//					} catch (Exception e) {
//						imprimirMensagem(e.getMessage(), "Ops...");
//					}
//				}
//
//				else if (tipo.toLowerCase().equals("unico")) {
//					try {
//						gerenciador.adicionarDespesa(data, valor, descricao,
//								Tipo.FIXO, categoria, numeroDeParcelas);
//						imprimirMensagem(
//								"Transacao Adicionada com Sucesso !!!",
//								"Ops...");
//						telaPrincipal();
//						
//					} catch (Exception e) {
//						imprimirMensagem(e.getMessage(), "Ops...");
//					}
//				}
			}
		});

	}

	public void telaAdicionarReceita() {
		setContentView(R.layout.adicionar);

		Spinner spinnerTipo = (Spinner) findViewById(R.adicionar.spinnerTipo);
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
				this, R.array.categorias, android.R.layout.simple_spinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerTipo.setAdapter(adapter2);

		// Adicionando na lista.

		Button btAdd = (Button) findViewById(R.adicionar.btAdd);

		btAdd.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				String descricao = ((EditText) findViewById(R.adicionar.editTextDescricao))
						.getText().toString();
				Double valor = Double
						.parseDouble(((EditText) findViewById(R.adicionar.valor))
								.getText().toString());
//				String tipo = ((Spinner) findViewById(R.adicionar.spinnerTipo))
//						.getSelectedItem().toString();
				String data = ((EditText) findViewById(R.adicionar.editTextData))
						.getText().toString();
				String categoria = ((Spinner) findViewById(R.adicionar.spinnerTipo))
						.getSelectedItem().toString();

//				if (tipo.toLowerCase().equals("fixo")) {
					try {
						gerenciador.adicionarReceita(data, valor, categoria, descricao);
						imprimirMensagem(
								"Transacao Adicionada com Sucesso !!!",
								"Sucesso");
						telaPrincipal();
						
					} catch (Exception e) {
						imprimirMensagem(e.getMessage(), "Ops...");
					}

//				} else if (tipo.toLowerCase().equals("mensalmente")) {
//					try {
//						gerenciador.adicionarReceita(data, valor,
//								Tipo.MENSALMENTE, descricao);
//						imprimirMensagem(
//								"Transacao Adicionada com Sucesso !!!",
//								"Sucesso");
//						telaPrincipal();
//						
//					} catch (Exception e) {
//						imprimirMensagem(e.getMessage(), "Ops...");
//					}
//
//				} else if (tipo.toLowerCase().equals("unico")) {
//
//					try {
//						gerenciador.adicionarReceita(data, valor, Tipo.UNICO,
//								descricao);
//						imprimirMensagem(
//								"Transacao Adicionada com Sucesso !!!",
//								"Sucesso");
//						telaPrincipal();
//						
//					} catch (Exception e) {
//						imprimirMensagem(e.getMessage(), "Ops...");
//					}
//				}
				// imprimirMensagem("Transacao adicionada com Sucesso",
				// "Sucesso");

				// Por enquanto dxa esse codigo da persistencia comentado...

				// BufferedWriter writer;
				// try {
				// writer = new BufferedWriter(new FileWriter(
				// Environment.getExternalStorageDirectory().getName() + "/"+
				// "pessoa.xml"));
				// String func =
				// xstream.toXML("passar aqui o objeto criado ou atualizado");
				// writer.write(func);
				// writer.close();
				// } catch (IOException e) {
				// e.printStackTrace();
				// }

			}
		});

	}

	/**
	 * Imprime uma mensagem de ao usu�rio
	 * 
	 * @param Caption
	 *            Mensagem a ser imprimida
	 * @param Title
	 *            Titulo da mensagem
	 */

	public void imprimirMensagem(String Caption, String Title) {
		AlertDialog.Builder builder = new AlertDialog.Builder(GFPActivity.this);
		builder.setMessage(Caption);
		builder.setNeutralButton("OK", null);
		AlertDialog dialog = builder.create();
		dialog.setTitle(Title);
		dialog.show();
	}

}