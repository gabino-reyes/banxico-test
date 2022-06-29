package com.grc.banxico.efirma.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public interface ISignatureBouncyService {

    X509Certificate loadCertificate(File file) throws FileNotFoundException, CertificateException, NoSuchProviderException;
    X509Certificate loadCertificate(String pem) throws CertificateException, NoSuchProviderException;
    PrivateKey getPrivateKey(File privateKeyFile, String passphrase);
    boolean verifySignRSAPKCS1(PublicKey publicKey, String originalMessage, String signEncode) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException;
}
