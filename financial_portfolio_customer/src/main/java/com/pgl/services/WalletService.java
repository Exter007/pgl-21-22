package com.pgl.services;

import com.pgl.models.FinancialProduct;
import com.pgl.models.Wallet;
import com.pgl.utils.GlobalVariables;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

public class WalletService extends HttpClientService<Wallet>{

    UserService userService = new UserService();

    private static Wallet currentWallet;

    /**
     *
     * Pour définir l'action à effectuer sur un object
     * false si création d'un nouvel object
     * true si modification d'un objet déjà existant
     */
    private static boolean edit = false;

    private static final String referencePath = "/wallet";

    public WalletService() {
        super(referencePath, Wallet.class, new ParameterizedTypeReference<List<Wallet>>() {});
    }

    public Wallet getCurrentWallet() {
        return currentWallet;
    }

    public void setCurrentWallet(Wallet currentWallet) {
        WalletService.currentWallet = currentWallet;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        WalletService.edit = edit;
    }

    public void moveCurrentWallet(){
        setCurrentWallet(null);
    }


    /**
     * Retrieve Wallet by Application Client
     * @return
     */
    public List<Wallet> getWalletsByClient(){
        String url = GlobalVariables.CONTEXT_PATH_CUSTOMER + referencePath +"/get-by-client/"
                + userService.getCurrentUser().getNationalRegister();

        return getListByURL(url);
    }

    public List<FinancialProduct> getWalletFinancialProductsById(){
        String url = GlobalVariables.CONTEXT_PATH_CUSTOMER + referencePath + "/" + currentWallet.getId() +"/product/list";
        HttpEntity<FinancialProduct> httpEntity = new HttpEntity<>(getHeaders());

        try {
            ResponseEntity<List<FinancialProduct>> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<FinancialProduct>>() {});
            System.out.println(response.getStatusCode());
            return response.getBody();

        }catch (HttpClientErrorException ex) {
            System.out.println("Exception : " + ex.getStatusCode() + " - " + ex.getMessage());

            if (ex.getStatusCode().equals(HttpStatus.UNAUTHORIZED) || ex.getMessage().contains("AccessDenied")) {
                showNotAuthException();
            } else {
                showOtherException();
            }
        }catch(Exception ex) {
            showException(ex);
        }
        return null;
    }
}
