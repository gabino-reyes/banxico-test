package com.grc.banxico.efirma.service.impl;

/**
 * Clase de servicio para manejar la librería bouncy castle en el manejo de criptografía
 * @author Gabino Reyes
 * @version 1.0
 * @since   2022-06-24
 */

import com.grc.banxico.efirma.service.ISignatureBouncyService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMDecryptorProvider;
import org.bouncycastle.openssl.PEMEncryptedKeyPair;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcePEMDecryptorProviderBuilder;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

@Service
public class SignatureBouncyServiceImpl implements ISignatureBouncyService {

    private static final Logger log = LoggerFactory.getLogger(SignatureBouncyServiceImpl.class);

    @Override
    public X509Certificate loadCertificate(File file) throws FileNotFoundException, CertificateException, NoSuchProviderException {
        X509Certificate cert;
        CertificateFactory factory;
        InputStream in = new FileInputStream(file);
        // Sin el provider manda otra data
        factory = CertificateFactory.getInstance("X.509", "BC");
        cert = (X509Certificate) factory.generateCertificate(in);
        return cert;
    }

    @Override
    public X509Certificate loadCertificate(String pem) throws CertificateException, NoSuchProviderException {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        CertificateFactory cf = CertificateFactory.getInstance("X.509", "BC");
        /*Reader reader = new StringReader(pem);
        PemReader pemReader = new PemReader(reader);
        X509Certificate cert;
        PemObject pemObject = pemReader.readPemObject();
        byte[] x509Data = pemObject.getContent();
        CertificateFactory fact = CertificateFactory.getInstance("X509", "BC");
        cert = (X509Certificate) fact.generateCertificate(new ByteArrayInputStream(x509Data));*/

        return (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(pem.getBytes()));
    }

    @Override
    public PrivateKey getPrivateKey(File privateKeyFile, String passphrase) throws IOException {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        PEMParser pemParser = new PEMParser(new FileReader(privateKeyFile));
        Object object = pemParser.readObject();
        PrivateKey privateKey;
        PrivateKeyInfo keyInfo;

        if (object instanceof PEMEncryptedKeyPair) {
            //log.info("PEMEncryptedKeyPair 1 ");
            if(passphrase == null)
                throw new RuntimeException("No se puede obtener la clave privada, frase de seguridad vacía.");

            PEMDecryptorProvider decryptor = new JcePEMDecryptorProviderBuilder().build(passphrase.toCharArray());
            PEMKeyPair decryptedKeyPair = ((PEMEncryptedKeyPair) object).decryptKeyPair(decryptor);
            keyInfo = decryptedKeyPair.getPrivateKeyInfo();
            privateKey = new JcaPEMKeyConverter().getPrivateKey(keyInfo);
        } else {
            keyInfo = ((PEMKeyPair) object).getPrivateKeyInfo();
            privateKey = new JcaPEMKeyConverter().getPrivateKey(keyInfo);
        }
        return privateKey;
    }

    @Override
    public byte[] signRSAPKCS1(PrivateKey privateKey, String message) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException, UnsupportedEncodingException {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        Signature signature = Signature.getInstance("SHA1withRSA", "BC");
        signature.initSign(privateKey);
        signature.update(message.getBytes("UTF8"));
        return signature.sign();
    }

    @Override
    public boolean verifySignRSAPKCS1(PublicKey publicKey, String originalMessage, String signEncode) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        byte[] sign = Base64.decodeBase64(signEncode.getBytes("UTF8"));
        Signature signer = Signature.getInstance("SHA1withRSA", "BC");
        signer.initVerify(publicKey);
        signer.update(originalMessage.getBytes("UTF8"));
        return signer.verify(sign);
    }
}
