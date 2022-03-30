package com.pgl.services;

import com.pgl.models.FinancialProductHolder;
import com.pgl.utils.GlobalVariables;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class ProductHolderService extends HttpClientService<FinancialProductHolder>{

    UserService userService = new UserService();

    private static FinancialProductHolder currentClient;

    /**
     *
     * Pour définir l'action à effectuer sur un object
     * false si création d'un nouvel object
     * true si modification d'un objet déjà existant
     */
    private static boolean edit = false;

    private static final String referencePath = "/holder";

    /**
     * Constructor vide
     * ParameterizedTypeReference pour la deserialisation du JSON recu de Rest API en Liste de Financial Product Holder
     * FinancialProductHolder.class pour la deserialisation du JSON recu de Rest API en Financial Product Holder
     */
    public ProductHolderService() {
        super(referencePath, FinancialProductHolder.class,
                new ParameterizedTypeReference<List<FinancialProductHolder>>() {});
    }

    /**
     * Get the current customer selected in the list
     * @return
     */
    public FinancialProductHolder getCurrentClient() {
        return currentClient;
    }

    /**
     * Set the current customer selected in the list
     * @param currentClient
     */
    public void setCurrentClient(FinancialProductHolder currentClient) {
        ProductHolderService.currentClient = currentClient;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        ProductHolderService.edit = edit;
    }

    public void moveCurrentClient(){
        setCurrentClient(null);
    }

    /**
     * Retrieve Customers from a Financial Institution
     * @return
     */
    public List<FinancialProductHolder> getHolderByInstitution(){
        String url = GlobalVariables.CONTEXT_PATH_INSTITUTION + referencePath +"/get-by-institution/"
                + userService.getCurrentUser().getBIC();

        return getListByURL(url);
    }
}