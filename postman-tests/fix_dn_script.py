import json

with open('postman-tests/StudentManagement-AutoTest.postman_collection.json', encoding='utf-8') as f:
    data = json.load(f)

dn_folder = next(x for x in data['item'] if x['name'] == 'DANG NHAP')
dn_request = dn_folder['item'][0]

new_exec = [
    "var tcId = pm.iterationData.get('tc_id');",
    "var expectedSuccess = pm.iterationData.get('expected_success');",
    "var expectedLoai = pm.iterationData.get('expected_loai');",
    "var expectedStatus = parseInt(pm.iterationData.get('expected_status'));",
    "pm.test('[' + tcId + '] HTTP Status = ' + expectedStatus, function () { pm.response.to.have.status(expectedStatus); });",
    "try {",
    "  var jsonBody = pm.response.json();",
    "  if (jsonBody.success !== undefined) {",
    "    pm.test('[' + tcId + '] success = ' + expectedSuccess, function () {",
    "      pm.expect(jsonBody.success).to.eql(expectedSuccess === 'true' || expectedSuccess === true);",
    "    });",
    "  } else {",
    "    pm.test('[' + tcId + '] response la loi he thong (no success field)', function () {",
    "      pm.expect(expectedSuccess === 'false' || expectedSuccess === false).to.be.true;",
    "    });",
    "  }",
    "} catch(e) {",
    "  pm.test('[' + tcId + '] response khong phai JSON - expected error', function () {",
    "    pm.expect(expectedSuccess === 'false' || expectedSuccess === false).to.be.true;",
    "  });",
    "}",
    "if ((expectedSuccess === 'true' || expectedSuccess === true) && expectedLoai && expectedLoai !== '') {",
    "  pm.test('[' + tcId + '] loaiNguoiDung = ' + expectedLoai, function () {",
    "    pm.expect(pm.response.json().loaiNguoiDung).to.eql(expectedLoai);",
    "  });",
    "}"
]

for ev in dn_request['event']:
    if ev['listen'] == 'test':
        ev['script']['exec'] = new_exec

with open('postman-tests/StudentManagement-AutoTest.postman_collection.json', 'w', encoding='utf-8') as f:
    json.dump(data, f, ensure_ascii=False, indent=2)

print('Done')
