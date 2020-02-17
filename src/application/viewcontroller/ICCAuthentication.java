package application.viewcontroller;

import application.CAuthenticationMenu;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * <p>Classe Input Controller della schermata di autenticazione</p>
 * @author Ciro Cozzolino
 */

public class ICCAuthentication { //Classe per l input control della schermata di autenticazione

	@FXML
	private TextField _oNameTextField;
	
	@FXML
	private TextField _oSurnameTextField;
	
	private CAuthenticationMenu _oMainClass;
	
	public ICCAuthentication() 
	{
		
	}
	
	@FXML
	private void initialize() 
	{
		
	}
	
	@FXML
	private void onNextButt() //Funzione applicata al tasto avanti
	{
		if (_oNameTextField.getText().length() > 0 && _oSurnameTextField.getText().length() > 0) 
		{
			_oMainClass.setUser(_oNameTextField.getText().toUpperCase(),_oSurnameTextField.getText().toUpperCase());
			_oMainClass.gotoNextMenu();
		}
	}
	
	public void setMainClass(CAuthenticationMenu oMainClass) 
	{
		this._oMainClass = oMainClass;
	}
	
}
