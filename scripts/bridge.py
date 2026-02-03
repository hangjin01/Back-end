# ... (상단 연결 설정 동일)
def on_message(client, userdata, msg):
    try:
        data = json.loads(msg.payload.decode('utf-8'))

        json_body = [{
            "measurement": "farm_data", # 측정 명칭 변경
            "tags": {"sensor_id": data['sensor_id']},
            "fields": {
                "temp": float(data['temp']),
                "humidity": float(data['humidity']),
                "soil_moisture": float(data['soil_moisture']),
                "co2": int(data['co2']),
                "light": int(data['light'])
            }
        }]
        db_client.write_points(json_body)
        print(f"DB 저장 완료: {data['sensor_id']} 데이터")
    except Exception as e:
        print(f"에러: {e}")