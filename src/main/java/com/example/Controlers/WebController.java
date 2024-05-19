package com.example.Controlers;

import com.example.Structures.Cron;
import com.example.Structures.Schedule;
import com.example.Structures.Teracota;
import com.example.Structures.Customer;
import org.apache.coyote.Request;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebController {

    private final ChiliPeperApplication chiliPeperApplication;
    public WebController(ChiliPeperApplication chiliPeperApplication) {
        this.chiliPeperApplication = chiliPeperApplication;
    }
    @GetMapping("/")
    public String toHome(){
        return "index";
    }    @GetMapping("/403")
    public String to403(){
        return "403";
    }
    @GetMapping("/ViewTables")
    //@ResponseBody
    public String ViewTables(){
        return "ViewTables";
    }
//region [customer]
    @GetMapping("/registry")
    public String toRegistry(){return "registry";}
    @PostMapping("/registry")
    public String toRegistryAgain(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) String password2)
    {
        if(ChiliPeperApplication.getUserID(username)!=-1) return "registry";
        if(!password.equals(password2)||password.equals("")) return "registry";
        ChiliPeperApplication.registryNewUser(username, password);
        return "redirect:";
    }
    @GetMapping("/login")
    public String tryToLogging(){
        return "login";
    }
    @PostMapping("/login")
    public String tryToLogging(
            @RequestParam String username,
            @RequestParam String password
    ){
        int userID=ChiliPeperApplication.connectUser(username,password);
        if(userID >-1)    return "redirect:userHome?id="+userID;
        else return "login";
    }
    @GetMapping("/userHome")
    public String toUserHome(){
        return "userHome";
    }
    @GetMapping( "/ChangePassword")
    public String toChangePassword( @RequestParam String id)
    {
        return "changePassword";
    }
    @PostMapping( "/ChangePassword")
    public String ChangePassword( @RequestParam String id,
                                  @RequestParam String oldPassword,
                                  @RequestParam String password,
                                  @RequestParam String password2)
    {
        if(!password.equals(password2)||password.equals("")) return "changePassword";
        if(!ChiliPeperApplication.changePassword(Integer.valueOf(id),oldPassword,password)) return "changePassword";

        return "redirect:login";
    }
    //endregion

//region [teracota]
    @GetMapping( value = "/newTeracota")
    public String newTeracota(String name, Teracota.PlantTypes pt)
    {
        return "newTeracota";
    }

    @PostMapping("/newTeracota")
    public String addTeracota(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String plantType)
    {
        Teracota newTeracota = new Teracota(name, Teracota.PlantTypes.valueOf(plantType));
        Customer currentUser = ChiliPeperApplication.getUser(Integer.valueOf(id));
        ChiliPeperApplication.addTeracota(currentUser,newTeracota);
        return "redirect:userHome?id="+id;
    }
    @GetMapping( value = "/teracotaDetail")
    public String detailTeracota(@RequestParam (required = false) String id,
                                 @RequestParam (required = false) String teracota,
                                 @RequestParam (required = false) String method)
    {
        if (method == null) return "teracotaDetail";
        if(method.equals("Delete"))
        {
            ChiliPeperApplication.deleteTeracota(Integer.valueOf(teracota));
            return "redirect:userHome?id="+id;
        }
        return "teracotaDetail";
    }
    @PostMapping("/teracotaDetail")
    public String ChangeTeracota(@RequestParam String id,
                                 @RequestParam String teracota,
                                 @RequestParam(required = false, value="start") String[] startsTime,
                                 @RequestParam(required = false, value="end") String[] endsTime,
                                 @RequestParam(required = false, value="temp") String[] temps,
                                 @RequestParam(required = false, value="light") String[] lights,
                                 @RequestParam(required = false, value="cronID") String[] cronsID,
                                 @RequestParam(required = false, value="schedlID") String[] scheduleID)
    {
        if (startsTime==null) return "redirect:teracotaDetail?id="+id+"&&teracota="+teracota;
        else //save crons
        {
            for (int i=0;i<startsTime.length;i++)
            {
                Schedule updateSchedl = new Schedule(Integer.valueOf(scheduleID[i]),Float.valueOf(temps[i]),Float.valueOf(lights[i]),Integer.valueOf(0));
                Cron cron = new Cron(Integer.valueOf(cronsID[i]),updateSchedl,Integer.valueOf(startsTime[i]),Integer.valueOf(endsTime[i]));
                ChiliPeperApplication.updateCron(cron);
            }
            return "redirect:userHome?id="+id;
        }
    }
    @GetMapping("/ChangeCronNumber")
    public String ChangeCron(@RequestParam String id,
                             @RequestParam String teracota,
                             @RequestParam String cronID,
                             @RequestParam (required = false) String method)
    {
        if (method == null)
        {
            return "ChangeCron";
        }
        if(method.equals("Delete"))
        {
            ChiliPeperApplication.deleteCron(Integer.valueOf(cronID));
            return "redirect:teracotaDetail?id="+id+"&&teracota="+teracota;
        }
        if (method.equals("Post"))
        {
            ChiliPeperApplication.addNewCron(Integer.valueOf(teracota));
            return "redirect:teracotaDetail?id="+id+"&&teracota="+teracota;
        }

        return "redirect:userHome?id="+id;
    }
    //endregion
}