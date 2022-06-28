package com.grc.banxico.efirma.util;

import org.apache.tomcat.util.codec.binary.Base64;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.pkcs.EncryptedPrivateKeyInfo;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMDecryptorProvider;
import org.bouncycastle.openssl.PEMEncryptedKeyPair;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JceOpenSSLPKCS8DecryptorProviderBuilder;
import org.bouncycastle.openssl.jcajce.JcePEMDecryptorProviderBuilder;
import org.bouncycastle.operator.InputDecryptorProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.pkcs.PKCS8EncryptedPrivateKeyInfo;
import org.bouncycastle.pkcs.PKCSException;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import javax.security.auth.x500.X500Principal;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class GenerateInsert {
    public static void main(String[] args) throws CertificateException, NoSuchProviderException, IOException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, OperatorCreationException, PKCSException, InvalidKeySpecException {
        Security.addProvider(new BouncyCastleProvider());
        File file = new File("src/main/resources/data/usuarios/usuario00/00000100000700000020.crt");
        File filePrivateKey = new File("src/main/resources/data/usuarios/usuario00/usuario00.cve");

        CertificateFactory certFactory= CertificateFactory.getInstance("X.509", "BC");
        getPrivateKey(filePrivateKey, "usuario00");
        /*String key = new String(Files.readAllBytes(file.toPath()), Charset.defaultCharset());
        System.out.println("key = " + key);
        X509Certificate certificate = (X509Certificate) certFactory.generateCertificate(new FileInputStream("src/main/resources/data/usuarios/usuario00/00000100000700000020.crt"));
        certificate.getPublicKey();
        X500Principal principal = certificate.getSubjectX500Principal();

        X500Name x500name = new X500Name( principal.getName() );
        RDN cn = x500name.getRDNs(BCStyle.CN)[0];



        /*String publicKeyPEM = key
                .replace("-----BEGIN CERTIFICATE-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END CERTIFICATE-----", "");

        System.out.println("publicKeyPEM = " + publicKeyPEM);

        byte[] encoded = Base64.decodeBase64(publicKeyPEM);

        System.out.println("encoded = " + encoded);

        //System.out.println("serial: " + certificate.getSerialNumber().toString(16));
        //System.out.println("cn: " + cn.getFirst().getValue());

        /*char[] keystorePassword = "password".toCharArray();
        char[] keyPassword = "password".toCharArray();

        KeyStore keystore = KeyStore.getInstance("PKCS12");
        keystore.load(new FileInputStream("Baeldung.p12"), keystorePassword);
        PrivateKey key = (PrivateKey) keystore.getKey("baeldung", keyPassword);*/
    }

    public static void get() throws IOException {
        /*File file = new File("src/main/resources/data/usuarios/usuario00/00000100000700000020.crt");
        PemReader pemReader = new PemReader(new FileReader(file)) {

            PemObject pemObject = pemReader.readPemObject();
            byte[] content = pemObject.getContent();
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(content);
            return (RSAPublicKey) factory.generatePublic(pubKeySpec);
        }*/
    }

    public static void getPrivateKey(File privateKeyFile, String passphrase) throws IOException, PKCSException, OperatorCreationException, NoSuchAlgorithmException, InvalidKeySpecException {
        // Se agrega el provider para encriptar PKCS8 keys
        Security.addProvider(new BouncyCastleProvider());
        System.out.println("privateKeyFile = " + privateKeyFile);
        byte[] key2 = Files.readAllBytes(privateKeyFile.toPath());
        System.out.println("key2 = " + key2);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(key2);
        PrivateKey finalKey = keyFactory.generatePrivate(keySpec);
        System.out.println(finalKey.getAlgorithm());


        System.out.println("============================================================================ ");



        String key = new String(Files.readAllBytes(privateKeyFile.toPath()), Charset.defaultCharset());
        //System.out.println("key = " + key);
        // reads your key file
        PEMParser pemParser = new PEMParser(new FileReader(privateKeyFile));
        Object object = pemParser.readObject();
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
        KeyPair kp;

        if (object instanceof PEMEncryptedKeyPair) {
            System.out.println("object 1 ");
            // Encrypted key - we will use provided password
            PEMEncryptedKeyPair ckp = (PEMEncryptedKeyPair) object;
            // uses the password to decrypt the key
            PEMDecryptorProvider decProv = new JcePEMDecryptorProviderBuilder().build(passphrase.toCharArray());
            kp = converter.getKeyPair(ckp.decryptKeyPair(decProv));
        } else {
            // Unencrypted key - no password needed
            System.out.println("object 2 ");
            PEMKeyPair ukp = (PEMKeyPair) object;
            kp = converter.getKeyPair(ukp);
        }

// RSA
        KeyFactory keyFac = KeyFactory.getInstance("RSA");
        RSAPrivateCrtKeySpec privateKey = keyFac.getKeySpec(kp.getPrivate(), RSAPrivateCrtKeySpec.class);

        System.out.println(privateKey.getClass());




        //ASN1Sequence asn1Sequence = ASN1Sequence.getInstance(Files.readAllBytes(privateKeyFile.toPath()));
        /*KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncryptedPrivateKeyInfo pkcs8EncryptedPrivateKeyInfo = new PKCS8EncryptedPrivateKeyInfo(EncryptedPrivateKeyInfo.getInstance(key));
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
        InputDecryptorProvider decryptorProvider = new JceOpenSSLPKCS8DecryptorProviderBuilder().build(passphrase.toCharArray());
        PrivateKeyInfo privateKeyInfo = pkcs8EncryptedPrivateKeyInfo.decryptPrivateKeyInfo(decryptorProvider);
        PrivateKey privateKey = converter.getPrivateKey(privateKeyInfo);
        System.out.println("privateKey = " + privateKey.getAlgorithm());
        System.out.println("privateKey = " + privateKey);*/
    }
}
