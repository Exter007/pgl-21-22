package com.pgl.services;

import com.pgl.utils.ContextName;
import org.springframework.stereotype.Service;

/**
 * Service for client application context
 */
@Service()
public class ContextService {

    public ContextName contextName = ContextName.CLIENT;

    /**
     * Get Application Context name
     * @return
     */
    public ContextName getContextName() {
        return contextName;
    }

    /**
     * Set Application Context name
     * @param contextName
     */
    public void setContextName(ContextName contextName) {
        this.contextName = contextName;
    }
}
