package com.pgl.services;

import com.pgl.models.Wallet;
import com.pgl.utils.GlobalVariables;

import java.util.List;

public class WalletService extends HttpClientService<Wallet>{

    private static Wallet currentWallet;

    /**
     *
     * Pour définir l'action à effectuer sur un object
     */
    // @false si création d'un nouvel object
    // @true si modification d'un objet déjà existant
    public static boolean edit = false;

    private static final String referencePath = "/wallet";

    public WalletService() {
        super(referencePath);
    }

    public static Wallet getCurrentWallet() {
        return currentWallet;
    }

    public static void setCurrentWallet(Wallet currentWallet) {
        WalletService.currentWallet = currentWallet;
    }

    public void moveCurrentWallet(){
        setCurrentWallet(null);
    }

    public List<Wallet> getWalletsByClient(){
        String url = GlobalVariables.CONTEXT_PATH_CUSTOMER + referencePath +"/get-by-client/"
                + UserService.getCurrentUser().getNationalRegister();

        return getListByURL(url);
    }
}
