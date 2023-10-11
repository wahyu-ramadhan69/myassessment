package com.recruitment.myassessment.service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.recruitment.myassessment.Configuration.OtherConfiguration;
import com.recruitment.myassessment.core.security.JwtUtility;
import com.recruitment.myassessment.handler.ResponseHandler;
import com.recruitment.myassessment.model.Usr;
import com.recruitment.myassessment.repo.UserRepo;

@Service
@Transactional
public class UsrService implements UserDetailsService {

    private UserRepo usrRepo;
    private JwtUtility jwtUtility;
    private String[] strExceptionArr = new String[2];
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsrService(UserRepo usrRepo, JwtUtility jwtUtility) {
        strExceptionArr[0] = "UsrService";
        this.usrRepo = usrRepo;
        this.jwtUtility = jwtUtility;
    }

    public ResponseEntity<Object> registrationUser(Usr usr, HttpServletRequest request) {// RANGE 001-005
        try {
            if (usr == null) {
                return new ResponseHandler().generateResponse(
                        "Data tidak Valid", // message
                        HttpStatus.BAD_REQUEST, // httpstatus
                        null, // object
                        "FV-Auth001",
                        request);
            }
            Random random = new Random();
            Integer intToken = random.nextInt(100000, 999999);
            String strToken = String.valueOf(intToken);
            Usr usrNext = null;// untuk penampungan jika proses update
            /*
             * pengecekan untuk memastikan user registrasi pertama kali atau sudah pernah
             * dan melakukan registrasi lagi
             * tetapi belum selesai melakukan otentikasi verifikasi email
             */
            Optional<Usr> optionalUsr = usrRepo.findByUserNameAndIsActive(usr.getUserName(), false);
            if (optionalUsr.isEmpty()) {
                /*
                 * Jika user baru maka informasi nya akan langsung di save
                 */
                usr.setPassword(bCryptPasswordEncoder.encode(usr.getPassword() + OtherConfiguration.getFlagPwdTrap()));// encrypt
                                                                                                                       // password
                                                                                                                       // sebelum
                                                                                                                       // ke
                                                                                                                       // database
                usr.setToken(bCryptPasswordEncoder.encode(strToken));
                usrRepo.save(usr);
            } else {
                /*
                 * Jika user sudah pernah registrasi tetapi gagal maka informasi sebelumnya akan
                 * ditiban
                 * Proses update
                 */
                usrNext = optionalUsr.get();
                usrNext.setPassword(usr.getPassword());
                usrNext.setEmail(usr.getEmail());
                usrNext.setToken(bCryptPasswordEncoder.encode(strToken));
            }
            String[] strVerify = new String[3];
            strVerify[0] = "Verifikasi Email";
            strVerify[1] = usr.getNama();
            strVerify[2] = strToken;

            // new ExecuteSMTP().sendSMTPToken(usr.getEmail(),"TOKEN Verifikasi
            // Email",strVerify,"\\data\\ver_regis.html");
        } catch (Exception e) {
            return new ResponseHandler().generateResponse(
                    "Data Gagal Disimpan", // message
                    HttpStatus.INTERNAL_SERVER_ERROR, // httpstatus
                    null, // object
                    "FE-Auth001", // errorCode Fail Error modul-code 001 sequence 001 range 001 - 010
                    request);
        }

        return new ResponseHandler().generateResponse(
                "Registrasi Berhasil Diproses", // message
                HttpStatus.CREATED, // httpstatus created
                null,
                null,
                request);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = null;
        Optional<Usr> optionalUsr = usrRepo.findByUserNameOrEmail(userName, userName);

        if (optionalUsr.isEmpty()) {
            return user;
        }
        Usr usr = optionalUsr.get();
        // $2a$11$Owf6JAbkUwLBisLGnmmD8u41FRk/Hs5oEt2byIHz9ENOk00oqU4ii
        return new User(usr.getUserName(), usr.getPassword(), new ArrayList<>());
    }

}
