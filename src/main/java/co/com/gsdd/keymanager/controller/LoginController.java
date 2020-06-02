package co.com.gsdd.keymanager.controller;

import co.com.gsdd.constantes.ConstantesInterfaz;
import co.com.gsdd.constants.GUIConstants;
import co.com.gsdd.constants.GralConstants;
import co.com.gsdd.gui.util.JOptionUtil;
import co.com.gsdd.keymanager.ejb.UsuarioEjb;
import co.com.gsdd.keymanager.entities.Usuario;
import co.com.gsdd.keymanager.enums.RolEnum;
import co.com.gsdd.keymanager.view.LoginView;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Great System Development Dynamic <GSDD> <br>
 *         Alexander Galvis Grisales <br>
 *         alex.galvis.sistemas@gmail.com <br>
 * @version 1.0
 */
@Slf4j
@Getter
@Setter
public class LoginController {
    /**
     * la instancia obtenida de la vista.
     */
    private LoginView view;
    /**
     * la instancia obtenida del modelo.
     */
    private UsuarioEjb modelo;
    /**
     * lleva los datos de sesi\u00f3n.
     */
    private Usuario dto;
    /**
     * La instancia definida de la clase.
     */
    private static final LoginController INSTANCE = new LoginController();

    /**
     * Constructor por Defecto.
     */
    private LoginController() {
        this.view = LoginView.getInstance();
    }

    /**
     * limpia los campos de texto.
     */
    public void clearText() {
        view.getTextUsuario().setText(GralConstants.EMPTY);
        view.getTextPass().setText(GralConstants.EMPTY);
    }

    /**
     * @param flag
     *            indica si los componentes se ven o no.
     */
    public void enableComponents(boolean flag) {
        view.getLabelUsuario().setVisible(flag);
        view.getLabelPass().setVisible(flag);
        view.getTextUsuario().setVisible(flag);
        view.getTextPass().setVisible(flag);
        view.getBtnIngresar().setVisible(flag);
    }

    /**
     * accion de logeado.
     */
    public void getLoged() {
        enableComponents(false);
    }

    /**
     * estado inicial del panel.
     */
    public void getInit() {
        clearText();
        PrincipalController.getInstance().getView().getMenuSesion().setText(GralConstants.EMPTY);
        enableComponents(true);
    }

    /**
     * logea al usuario en el sistema.
     */
    public void autenticar() {
        try {
            // Primer conexion con BD Lenta?
            this.modelo = UsuarioEjb.getInstance();
            String username = view.getTextUsuario().getText().trim();
            String pass = new String(view.getTextPass().getPassword()).trim();
            dto = modelo.login(username, pass);
            if (dto != null) {
                // acciones de seteo de login.
                getLoged();
                dto.setUsername(username);
                PrincipalController.getInstance().changeTitle(ConstantesInterfaz.TITULO_LOGIN);
                String sesion = ConstantesInterfaz.LABEL_LOGGED + dto.getPrimerNombre() + " " + dto.getPrimerApellido();
                PrincipalController.getInstance().getView().getMenuSesion().setText(sesion);
                PrincipalController.getInstance().getLoControl().setDto(dto);
                if (!RolEnum.ADMIN.getCode().equals(String.valueOf(dto.getRol()))) {
                    PrincipalController.getInstance().getView().getMenuAdmon()
                            .remove(PrincipalController.getInstance().getView().getItemConsulta());
                }
            } else {
                log.info(ConstantesInterfaz.E_MSJ_NO_EXISTE);
                JOptionUtil.showErrorMessage(GUIConstants.ERROR, ConstantesInterfaz.E_MSJ_NO_EXISTE);
                view.getTextPass().setText(GralConstants.EMPTY);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            JOptionUtil.showErrorMessage(GUIConstants.ERROR, e.getMessage());
        }
    }

    /**
     * @return the instance
     */
    public static LoginController getInstance() {
        return INSTANCE;
    }

}
