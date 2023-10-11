package com.recruitment.myassessment.service;

import java.util.Optional;
import java.util.Random;

import javax.management.modelmbean.ModelMBean;
import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.recruitment.myassessment.handler.ResponseHandler;
import com.recruitment.myassessment.model.User;
import com.recruitment.myassessment.repo.UserRepo;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public ResponseEntity<Object> registrationUser(User user, HttpServletRequest request) {// RANGE 001-005
        try {
            if (user == null) {
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
            User usrNext = null;// untuk penampungan jika proses update
            /*
             * pengecekan untuk memastikan user registrasi pertama kali atau sudah pernah
             * dan melakukan registrasi lagi
             * tetapi belum selesai melakukan otentikasi verifikasi email
             */
            Optional<User> optionalUsr = userRepo.findByEmailAndIsActive(user.getEmail(), false);
            if (optionalUsr.isEmpty()) {
                /*
                 * Jika user baru maka informasi nya akan langsung di save
                 */
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));// encrypt password sebelum ke
                                                                                   // database
                user.setToken(bCryptPasswordEncoder.encode(strToken));
                userRepo.save(user);
            } else {
                /*
                 * Jika user sudah pernah registrasi tetapi gagal maka informasi sebelumnya akan
                 * ditiban
                 * Proses update
                 */
                usrNext = optionalUsr.get();
                usrNext.setPassword(user.getPassword());
                usrNext.setEmail(user.getEmail());
                usrNext.setToken(bCryptPasswordEncoder.encode(strToken));
            }
            String[] strVerify = new String[3];
            strVerify[0] = "Verifikasi Email";
            strVerify[1] = user.getNama();
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
}
