/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package het.springapp.security;

/**
 *
 * @author heather
 */

import org.springframework.security.web.context.*;
import het.springapp.security.SecurityAdapter;

public class SecurityWebApplicationInitializer
      extends AbstractSecurityWebApplicationInitializer {

    public SecurityWebApplicationInitializer() {
        super(SecurityAdapter.class);
    }
}
