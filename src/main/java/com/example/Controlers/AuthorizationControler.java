package com.example.Controlers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthorizationControler {

    DatabaseControler dbControler = new DatabaseControler();
    public boolean isCustomerAuthorize(int id, String token)
    {
        String storedToken = dbControler.getUserToken(id);
        return (storedToken !=null && storedToken.equals(getHash(token)));
    }
    public boolean isPasswordCorect(int id, String password)
    {
        String storedpassword = dbControler.getUserPassword(id);
        return (storedpassword !=null && storedpassword.equals(getHash(password)));
    }
    public int LogUser(String userName, String password,String token)
    {
        String storedPassword = dbControler.getUserPassword(userName);
        if(storedPassword !=null && storedPassword.equals(getHash(password)))
        {
            int userID = dbControler.getUserID(userName);
            dbControler.setCustomerToken(userID, getHash(token));
            return userID;
        }
        else return -1;
    }
    public void LogOutUser(int userID)
    {
        dbControler.setCustomerToken(userID,null);
    }
    public boolean changePassword(int id, String password, String newPassword)
    {
        String storedPassword = dbControler.getUserPassword(id);
        if(storedPassword !=null && storedPassword.equals(getHash(password)))
        {
            dbControler.changeUser(id,dbControler.getUser(id).getUserName(), getHash(newPassword));
            return true;
        }
        return false;
    }
    public String getHash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean isCustomerOwnerOfTeracota(int customerID, int teracotaID)
    {
        return (customerID ==dbControler.getTeracotaOwner(teracotaID));
    }
}
