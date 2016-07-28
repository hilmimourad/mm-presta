package control.masterAdminApp;

import business.data.UtilisateurDAO;
import business.model.Utilisateur;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import utilities.MasterAdmin;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by mourad on 7/28/2016.
 */
@Controller
public class MasterAdminController {

    @RequestMapping(method = RequestMethod.GET)
    public String home(HttpServletRequest request, ModelMap map){
        if(request.getSession().getAttribute("user")==null){
            return "redirect:/admin/login.do";
        }
        else
        {
            map.addAttribute("roles", Utilisateur._ROLES);
            map.addAttribute("users", UtilisateurDAO.getAll());
            return "home";
        }

    }

    @RequestMapping(value = "login.do",method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(value = "logout.do",method = RequestMethod.GET)
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/admin/login.do";
    }



    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    public String loginPost(HttpServletRequest request, @RequestParam String username,@RequestParam String password){
        if(username==null || password==null){
            request.getSession().setAttribute("error","Veuillez remplir le formulaire");
            return "redirect:/admin/login.do";
        }

        if(username.trim().equals("") || password.trim().equals("")){
            request.getSession().setAttribute("error","Veuillez remplir le formulaire (Espace blanc n'est pas accepté)");
            return "redirect:/admin/login.do";
        }

        if(MasterAdmin.signIn(username,password)){
            request.getSession().invalidate();
            request.getSession().setAttribute("user",username);
            return "redirect:/admin/";
        }else{
            request.getSession().setAttribute("error","Nom d'utilisateur ou mot de passe incorrect");
            return "redirect:/admin/login.do";
        }
    }

    @RequestMapping(value = "/user/create.do",method = RequestMethod.POST)
    public String loginPost(HttpServletRequest request, @RequestParam String username,@RequestParam String password,@RequestParam int role){
        if(username==null || password==null || role==0){
            request.getSession().setAttribute("error","Veuillez remplir le formulaire");
            return "redirect:/admin/";
        }

        if(username.trim().equals("") || password.trim().equals("")){
            request.getSession().setAttribute("error","Veuillez remplir le formulaire (Espace blanc n'est pas accepté)");
            return "redirect:/admin/";
        }

        if(UtilisateurDAO.getUtilisateur(username)!=null){
            request.getSession().setAttribute("error","Nom d'utilisateur deja existant");
            return "redirect:/admin/";
        }

        Utilisateur u = new Utilisateur();
        u.setUsername(username);
        u.setPassword(password);
        u.setRole(role);

        if(UtilisateurDAO.insertUtilisateur(u)!=null){
            request.getSession().setAttribute("success","Utilisateur crée avec succès");
            return "redirect:/admin/";
        }else{
            request.getSession().setAttribute("error","Erreur base de données");
            return "redirect:/admin/";
        }
    }



    @RequestMapping(value = "nav.do",method = RequestMethod.GET)
    public String nav(){
        return "nav";
    }
}
