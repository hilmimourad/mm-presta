package control.masterAdminApp;

import business.data.UtilisateurDAO;
import business.model.Utilisateur;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import utilities.Encryptor;
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

    @RequestMapping(value="/profile.do",method = RequestMethod.GET)
    public String profile(HttpServletRequest request, ModelMap map){
        if(request.getSession().getAttribute("user")==null){
            return "redirect:/admin/login.do";
        }
        else
        {
            return "profile";
        }

    }

    @RequestMapping(value = "/password/change.do",method = RequestMethod.POST)
    public String changePassword(HttpServletRequest request, @RequestParam String oldPassword,@RequestParam String newPassword){
        if(request.getSession().getAttribute("user")==null){
            return "redirect:/admin/login.do";
        }

        if(oldPassword==null || newPassword==null){
            request.getSession().setAttribute("error","Veuillez remplir le formulaire");
            return "redirect:/admin/profile.do";
        }

        if(oldPassword.trim().equals("") || newPassword.trim().equals("")){
            request.getSession().setAttribute("error","Veuillez remplir le formulaire (Espace blanc n'est pas accepté)");
            return "redirect:/admin/profile.do";
        }

        if(!MasterAdmin.getPassword().equals(oldPassword)){
            request.getSession().invalidate();
            request.getSession().setAttribute("error","Vous avez était déconnecé automatiquement pour des raiosn de sécurité lors de l'opération de changement de mot de passe," +
                    " car vous avez fournie un faux Ancien Mot de passe. Vous pouvez à tout moment vous connectez à l'application si vous avez le mot de passe correcte.");
            return "redirect:/admin/login.do";
        }

        if(MasterAdmin.setPassword(newPassword)){
            request.getSession().setAttribute("success","Mot de passe changé avec succès");
            return "redirect:/admin/profile.do";
        }else{
            request.getSession().setAttribute("error","Erreur système");
            return "redirect:/admin/profile.do";
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
    public String createUser(HttpServletRequest request, @RequestParam String username,@RequestParam String password,@RequestParam int role){
        if(request.getSession().getAttribute("user")==null){
            return "redirect:/admin/login.do";
        }

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
        u.setPassword(Encryptor.encrypte(password));
        u.setRole(role);

        if(UtilisateurDAO.insertUtilisateur(u)!=null){
            request.getSession().setAttribute("success","Utilisateur crée avec succès");
            return "redirect:/admin/";
        }else{
            request.getSession().setAttribute("error","Erreur base de données");
            return "redirect:/admin/";
        }
    }

    @RequestMapping(value = "/user/{username}/delete.do",method = RequestMethod.GET)
    public String deleteUser(HttpServletRequest request, @PathVariable String username){
        if(request.getSession().getAttribute("user")==null){
            return "redirect:/admin/login.do";
        }

        if(username==null){
            request.getSession().setAttribute("error","Erreur, manque d'informations");
            return "redirect:/admin/";
        }

        if(username.trim().equals("")){
            request.getSession().setAttribute("error","Erreur manque d'informations");
            return "redirect:/admin/";
        }

        if(UtilisateurDAO.deleteUtilisateur(username)){
            request.getSession().setAttribute("success","utilisateur supprimé avec succès");
            return "redirect:/admin/";
        }else{
            request.getSession().setAttribute("error","Erreur base de données");
            return "redirect:/admin/";
        }

    }



    @RequestMapping(value = "/user/{username}/edit.do",method = RequestMethod.GET)
    public String editUser(ModelMap map,HttpServletRequest request, @PathVariable String username){
        if(request.getSession().getAttribute("user")==null){
            return "redirect:/admin/login.do";
        }

        if(username==null){
            request.getSession().setAttribute("error","Erreur, manque d'informations");
            return "redirect:/admin/";
        }

        if(username.trim().equals("")){
            request.getSession().setAttribute("error","Erreur manque d'informations");
            return "redirect:/admin/";
        }

       Utilisateur u = UtilisateurDAO.getUtilisateur(username);

        if(u==null){
            request.getSession().setAttribute("error","l'Utilisateur n'existe pas");
            return "redirect:/admin/";
        }

        map.addAttribute("user",u);
        map.addAttribute("roles",Utilisateur._ROLES);
        return "editUser";

    }

    @RequestMapping(value = "/user/password/change.do",method = RequestMethod.POST)
    public String changeUserPassword(HttpServletRequest request, @RequestParam String username,@RequestParam String password){
        if(request.getSession().getAttribute("user")==null){
            return "redirect:/admin/login.do";
        }

        if(password==null){
            request.getSession().setAttribute("error","Veuillez remplir le formulaire");
            return "redirect:/admin/"+username+"/edit.do";
        }

        if(password.trim().equals("")){
            request.getSession().setAttribute("error","Veuillez remplir le formulaire (Espace blanc n'est pas accepté)");
            return "redirect:/admin/"+username+"/edit.do";
        }

        Utilisateur u = UtilisateurDAO.getUtilisateur(username);

        if(u==null){
            request.getSession().setAttribute("error","l'utilisateur n'existe pas");
            return "redirect:/admin/";
        }

        u.setPassword(Encryptor.encrypte(password));

        if(UtilisateurDAO.updateUtilisateur(u)!=null){
            request.getSession().setAttribute("success","Mot de passe changé avec succès");
            return "redirect:/admin/"+username+"/edit.do";
        }else{
            request.getSession().setAttribute("error","Erreur base de données");
            return "redirect:/admin/"+username+"/edit.do";
        }
    }


    @RequestMapping(value = "/user/role/change.do",method = RequestMethod.POST)
    public String changeUserRole(HttpServletRequest request, @RequestParam String username,@RequestParam int role){
        if(request.getSession().getAttribute("user")==null){
            return "redirect:/admin/login.do";
        }

        if(role==0){
            request.getSession().setAttribute("error","Veuillez remplir le formulaire");
            return "redirect:/admin/"+username+"/edit.do";
        }

        Utilisateur u = UtilisateurDAO.getUtilisateur(username);

        if(u==null){
            request.getSession().setAttribute("error","L'utilisateur n'existe pas");
            return "redirect:/admin/";
        }

        u.setRole(role);

        if(UtilisateurDAO.updateUtilisateur(u)!=null){
            request.getSession().setAttribute("success","Role changé avec succès");
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
