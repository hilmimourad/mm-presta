package control.services.open;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <h1>Controleur Spring Rest de test</h1>
 * <p>Ce controleur permet de tester rapidement si le serveur est opérationel et les services sont en ligne</p>
 *
 * @author  Mourad Hilmi
 * @version 1.0
 * @since   2016-07-19
 *
 */
@RestController
@RequestMapping("/test")
class TestService {

    /**
     * Cette méthode est accessible via "${serviceUrl}/test/run", et permet de tester si le serveur des services est opérationnel
     * @return   Un message dont le Media Type est <b>TEXT/PLAIN</b>
     */
    @RequestMapping(value="/run.do",method = RequestMethod.GET,produces = MediaType.TEXT_PLAIN_VALUE)
    public String test(HttpServletRequest request, HttpServletResponse response){

        return "Services are up & running [^_^]";
    }
}
