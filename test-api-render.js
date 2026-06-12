// Test script để kiểm tra API trên Render
// Chạy với: node test-api-render.js

const BASE_URL = 'https://studentmanagementappbackend-3.onrender.com/api';

// Utility function để gọi API
async function callAPI(method, endpoint, data = null, token = null) {
    const url = `${BASE_URL}${endpoint}`;
    
    const options = {
        method: method.toUpperCase(),
        headers: {
            'Content-Type': 'application/json',
        }
    };
    
    if (token) {
        options.headers['Authorization'] = `Bearer ${token}`;
    }
    
    if (data && (method.toUpperCase() === 'POST' || method.toUpperCase() === 'PUT')) {
        options.body = JSON.stringify(data);
    }
    
    console.log(`🔄 ${method.toUpperCase()} ${url}`);
    
    try {
        const startTime = Date.now();
        const response = await fetch(url, options);
        const endTime = Date.now();
        const responseTime = endTime - startTime;
        
        const result = await response.json();
        
        console.log(`✅ ${response.status} - ${responseTime}ms`);
        console.log(`📊 Response:`, JSON.stringify(result, null, 2));
        console.log('---');
        
        return { success: response.ok, data: result, status: response.status };
    } catch (error) {
        console.error(`❌ Error calling ${url}:`, error.message);
        return { success: false, error: error.message };
    }
}

// Test chính
async function runTests() {
    console.log('🚀 Testing Student Management API on Render');
    console.log('Base URL:', BASE_URL);
    console.log('==========================================\n');
    
    let token = null;
    
    // 1. Test Login
    console.log('1️⃣ Testing Login...');
    const loginResult = await callAPI('POST', '/auth/login', {
        username: 'admin',
        password: 'admin123'
    });
    
    if (loginResult.success && loginResult.data.token) {
        token = loginResult.data.token;
        console.log('✅ Login successful! Token saved.\n');
    } else {
        console.log('❌ Login failed! Cannot continue with other tests.\n');
        return;
    }
    
    // 2. Test Get All Students
    console.log('2️⃣ Testing Get All Students...');
    await callAPI('GET', '/students', null, token);
    
    // 3. Test Get All Accounts (Admin only)
    console.log('3️⃣ Testing Get All Accounts (Admin only)...');
    await callAPI('GET', '/accounts', null, token);
    
    // 4. Test Register New Account
    console.log('4️⃣ Testing Register New Account...');
    await callAPI('POST', '/auth/register', {
        tenDangNhap: 'testapi' + Date.now(),
        matKhau: 'password123',
        loaiNguoiDung: 'SinhVien'
    });
    
    // 5. Test Invalid Login
    console.log('5️⃣ Testing Invalid Login...');
    await callAPI('POST', '/auth/login', {
        username: 'nonexistent',
        password: 'wrongpassword'
    });
    
    // 6. Test Logout
    console.log('6️⃣ Testing Logout...');
    await callAPI('POST', '/auth/logout', {
        username: 'admin'
    }, token);
    
    console.log('🏁 All tests completed!');
}

// Chạy tests
if (typeof fetch === 'undefined') {
    console.log('❌ This script requires Node.js 18+ or a fetch polyfill');
    console.log('💡 Alternatively, copy the URL and test manually in Postman');
    console.log('🔗 Login URL: https://studentmanagementappbackend-3.onrender.com/api/auth/login');
    console.log('📝 Login Body: {"username": "admin", "password": "admin123"}');
} else {
    runTests().catch(console.error);
}

// Export cho sử dụng khác
if (typeof module !== 'undefined' && module.exports) {
    module.exports = { callAPI, BASE_URL };
}