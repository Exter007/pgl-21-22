package com.pgl.services;

import com.pgl.models.RequestWallet;
import com.pgl.models.Wallet;
import com.pgl.utils.GlobalVariables;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class WalletService  extends HttpClientService<Wallet>{

    private static final String referencePath = "/wallet";

    public WalletService() {
        super(referencePath, Wallet.class, new ParameterizedTypeReference<List<Wallet>>() {});
    }


    public List<Wallet> getAllWallet(String bic) {
        String url = GlobalVariables.CONTEXT_PATH_INSTITUTION + referencePath + "/list/" + bic;
        return getListByURL(url);
    }

    public Wallet findById(Long id) {
        String url = GlobalVariables.CONTEXT_PATH_INSTITUTION + referencePath + "/find-by-id/" + id;
        return getByURL(url);
    }

    public Wallet createWallet(Wallet wallet){
        String url = GlobalVariables.CONTEXT_PATH_INSTITUTION + referencePath + "/save";
        return post(url, wallet);
    }
}
