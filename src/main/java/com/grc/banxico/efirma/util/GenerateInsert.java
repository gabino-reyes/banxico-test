package com.grc.banxico.efirma.util;

/**
 * Clase main para probar algoritmos y evitar levantar un servidor.
 * @author Gabino Reyes
 * @version 1.0
 * @since   2022-06-24
 */

import org.apache.tomcat.util.codec.binary.Base64;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.pkcs.ContentInfo;
import org.bouncycastle.asn1.pkcs.EncryptedPrivateKeyInfo;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cms.*;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
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
import java.util.Collection;

public class GenerateInsert {
    public static void main(String[] args) throws CertificateException, NoSuchProviderException, IOException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, OperatorCreationException, PKCSException, InvalidKeySpecException, SignatureException, InvalidKeyException {
        Security.addProvider(new BouncyCastleProvider());
        File fileCrt = new File("src/main/resources/data/usuarios/usuario00/00000100000700000020.crt");
        File filePrivateKey = new File("src/main/resources/data/usuarios/usuario00/usuario00.cve");
        String sign= "gaQEcacdXO0bS3U3k0AcLs4rJqmNIS9GKzzOOxlmKz0EV8PcmnFTrkb4RPdKu18A/RQmlYikHHWo68mrdkzoMXaMCvFVGyrANYFUfRV/71X2FDTvM2Z1IydxatBJ9NwTKbzzGjEsouzOayk6MqIOiSrph6zVxfUty2vspt5UmIZ7bdD6bJJpsZzQISKVgc9UT99bnclU/3TtQiWrf07LNpCIFQH52WrVEF5+dn5LgI41IE1p6Ob+r1Hi7h+oqmlYwFLBy5bR5CIDzbTPk8vqN+avg0i3m3IB6G3CPbqS5ynxVkW//B8tudGS98kwLWMOwqnyH42udstHo81PPrioXg==";
        X509Certificate certificate0 = getCrt(fileCrt);
        //System.out.println(verifySign(certificate0,sign));

        //getPrivateKey(filePrivateKey, "usuario00");
        //BigInteger bi = new BigInteger("76292708057987193002565060032465481997");
        //System.out.println(bi.toString(16));
        //String key = new String(Files.readAllBytes(fileCrt.toPath()), Charset.defaultCharset());
        //System.out.println("key = " + key);
        //CertificateFactory certFactory= CertificateFactory.getInstance("X.509", "BC");
        //X509Certificate certificate = (X509Certificate) certFactory.generateCertificate(new FileInputStream("src/main/resources/data/usuarios/usuario00/00000100000700000020.crt"));
        //System.out.println(certificate.getPublicKey());
        /*X500Principal principal = certificate.getSubjectX500Principal();

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
    public static X509Certificate getCrt() throws IOException, CertificateException, NoSuchProviderException {
        String file = "-----BEGIN CERTIFICATE-----\n" +
                "MIIFnDCCBGygAwIBAgIUMDAwMDAxMDAwMDA3MDAwMDAwMjAwDQYJKoZIhvcNAQEF\n" +
                "BQAwgbMxCzAJBgNVBAYTAk1YMQswCQYDVQQIEwJNWDELMAkGA1UEBxMCTVgxCzAJ\n" +
                "BgNVBAoTAkJNMQwwCgYDVQQDEwNBQzcxDjAMBgNVBC0TBTEyMzQ1MREwDwYGdYhd\n" +
                "jzUREwUxMjM0NTERMA8GBnWIXY81ExMFMTIzNDUxEjAQBgkqhkiG9w0BCQITA0VW\n" +
                "QzElMCMGCSqGSIb3DQEJARYWZXZjX2RzcEBiYW54aWNvLm9yZy5teDAeFw0xNzA4\n" +
                "MDgyMjUxMTBaFw0yMTA4MDgyMjUxMTBaMIIBDjELMAkGA1UEBhMCTVgxGTAXBgNV\n" +
                "BAgTEENpdWRhZCBkZSBNZXhpY28xGTAXBgNVBAcTEENpdWRhZCBkZSBNZXhpY28x\n" +
                "GDAWBgNVBAoTD0JhbmNvIGRlIE1leGljbzETMBEGA1UEAxMKdXN1YXJpbyAwMDER\n" +
                "MA8GBnWIXY81ERMFMDYwMDAxLDAqBgZ1iF2PNRMTIEF2IENpbmNvIGRlIE1heW8g\n" +
                "Tm8gMiBDb2wgQ2VudHJvMSAwHgYJKoZIhvcNAQkBFhFpZXNAYmFueGljby5vci5t\n" +
                "eDE3MDUGCSqGSIb3DQEJAhMoUmVzcG9uc2FibGU6IEx1aXMgQ2FybG9zIENvcm9u\n" +
                "YWRvIEdhcmNpYTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBALdDWfBr\n" +
                "xHZCOBfFTEBsrvrtPU+w9DXWB1DRjeF2nMVtA/sImRp0gDR4pAvU5/VEvST+IMmv\n" +
                "Q9up40zRNerPmx6Vb8+Q2DjaoCEvcyOcW8hIKnkU1hgXeqrMEfKs1nU7ZaCVQYG5\n" +
                "hlk1KUAzz7qtr/LjHGJbWlKAnYC0/8ORthWfnXuo8H1a6B/U2yhKcSsw/VMtqwYF\n" +
                "UMncK1gTCVjZyFeU7Q29iyWzxd0YVco46pUsz3L8x8bm2cA/e/C+tw8BQw4WiaDg\n" +
                "NRTFO1eyq4BMvKnt+uYwKqeUIEQDHhCJNVBzK68b6UByU2OST6h11HHRyqBNsRtk\n" +
                "8RxPefu95l9O4R0CAwEAAaOCATAwggEsMIHsBgNVHSMEgeQwgeGAFMjS8H2zfyZp\n" +
                "nQ/EE48N4jJ79IjcoYGypIGvMIGsMQswCQYDVQQGEwJNWDENMAsGA1UECAwERC5G\n" +
                "LjEZMBcGA1UEBwwQQ2l1ZGFkIGRlIE1leGljbzEYMBYGA1UECgwPQmFuY28gZGUg\n" +
                "TWV4aWNvMQwwCgYDVQQLDANEU1AxIzAhBgNVBAMMGkFnZW5jaWEgQ2VydGlmaWNh\n" +
                "ZG9yYSBCZXRhMSYwJAYJKoZIhvcNAQkBFhdsY2FicmVyYUBiYW54aWNvLm9yZy5t\n" +
                "eIIUMDAwMDAwMDAwMDAwMDAwMDAwMjUwHQYDVR0OBBYEFEdbI+4xflgC8RbHMZjN\n" +
                "DX7CRGeeMAwGA1UdEwEB/wQCMAAwDgYDVR0PAQH/BAQDAgTwMA0GCSqGSIb3DQEB\n" +
                "BQUAA4IBGQB5ejvN5SUpKKWlyI77Vw+Pd1iHvVIVgCFPn33//YBX3yYhkP/KUNVu\n" +
                "dcDu0i8vEMgnde5R6a1eVwP43CCGhYsOdVpkdhRHySBuD0qk9alcgtyCATIks+At\n" +
                "ef4mqqHMD8IWqEuviw/dfQEGozQMPBY4sQmhZAXeGGbIks+uH1vxWrT0p91wona/\n" +
                "05kU5j2/3yWxwZ2ZPAMuTuKbD0AwdPvB+eTYs56bqRFaastNE5y4nNe5c5iisV+s\n" +
                "d7ah+NMBX4yQc+TxsmyuhTU7OKbNEDQqoRh2MXEd+YsppJllKsvCz/CTVjEmIuM/\n" +
                "bxYFUutbnRPVCKbuI89oiD538AqtI+ecEEZY3Ozy4F7cYRjQ/xhANPFWggFfvZPc\n" +
                "-----END CERTIFICATE-----";
        Reader reader = new StringReader(file);
        System.out.println("reader = " + reader);
        PemReader pemReader = new PemReader(reader);
        System.out.println("pemReader = " + pemReader);
        X509Certificate cert = null;
        PemObject pemObject = pemReader.readPemObject();
        final byte[] x509Data = pemObject.getContent();
        final CertificateFactory fact = CertificateFactory.getInstance("X509", "BC");
        cert = (X509Certificate) fact.generateCertificate(new ByteArrayInputStream(x509Data));

        return cert;
    }

    public static  X509Certificate getCrt(String pem) throws CertificateException, NoSuchProviderException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509", "BC");
        X509Certificate cert = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(pem.getBytes()));
        return cert;
    }

    // OK
    public static X509Certificate getCrt(File file) throws IOException, CertificateException, NoSuchProviderException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        X509Certificate cert = null;
        CertificateFactory factory;
        //CertificateFactory certFactory= CertificateFactory.getInstance("X.509", "BC");
        InputStream in = new FileInputStream(file);
        //Sin el provider manda otra data
            factory = CertificateFactory.getInstance("X.509", "BC");
            cert = (X509Certificate) factory.generateCertificate(in);
        return cert;
    }
    //OK
    public static boolean verifySign(X509Certificate certificate, String signData) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException, OperatorCreationException, UnsupportedEncodingException {
        // SHA1withRSA  RSA/NONE/PKCS1Padding SHA384withRSA SHA256WithRSA
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        String originalData = "gabinousuario 003030303030313030303030373030303030303230";
        byte[] sign = Base64.decodeBase64(signData.getBytes("UTF8"));
        System.out.println("sign = " + sign.toString());

        Signature signer = Signature.getInstance("SHA1withRSA", "BC");
        signer.initVerify(certificate.getPublicKey());
        signer.update(originalData.getBytes("UTF8"));
        return signer.verify(sign);
        //return signer.verify(decodedBytes);
    }
    public static boolean verify(PublicKey publicKey, String message, String signature) throws SignatureException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        Signature sign = Signature.getInstance("SHA1withRSA");
        sign.initVerify(publicKey);
        sign.update(message.getBytes("UTF-8"));
        return sign.verify(Base64.decodeBase64(signature.getBytes("UTF-8")));
    }
    public static boolean verifySignature(PublicKey dsaPublic, byte[] input, byte[] encSignature)
            throws GeneralSecurityException {
        Signature signature = Signature.getInstance("SHA1withRSA", "BC");
        signature.initVerify(dsaPublic);
        signature.update(input);
        return signature.verify(encSignature);
    }


    //Example 27 – The PKCS#1.5 Signature Format
    public static byte[] generatePkcsSignature(PrivateKey rsaPrivateKey, byte[] input) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance("SHA384withRSA", "BCFIPS");
        signature.initSign(rsaPrivateKey);
        signature.update(input);
        return signature.sign();
    }

    public static boolean verifyPkcs1Signature(PublicKey rsaPublic, byte[] input, byte[] encSignature) throws GeneralSecurityException
    {
        Signature signature = Signature.getInstance("SHA384withRSA", "BCFIPS");
        signature.initVerify(rsaPublic);
        signature.update(input);
        return signature.verify(encSignature);
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
