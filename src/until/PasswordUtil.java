package until;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class để hash mật khẩu
 */
public class PasswordUtil {
    
    /**
     * Hash mật khẩu bằng SHA-256
     * @param password Mật khẩu gốc
     * @return Mật khẩu đã hash
     */
    public static String hashPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            return null;
        }
        
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            
            // Chuyển byte array thành hex string
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
            
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Xác thực mật khẩu
     * @param plainPassword Mật khẩu người dùng nhập
     * @param hashedPassword Mật khẩu đã hash trong DB
     * @return true nếu mật khẩu đúng
     */
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) {
            return false;
        }
        
        String inputHash = hashPassword(plainPassword);
        return inputHash != null && inputHash.equals(hashedPassword);
    }
}