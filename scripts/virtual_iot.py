import paho.mqtt.client as mqtt
import time
import random
import json

broker_address = "localhost"
topic = "home/livingroom/sensors" # 토픽명을 sensors로 변경 권장

client = mqtt.Client(mqtt.CallbackAPIVersion.VERSION2, "Virtual_IoT_Board")
client.connect(broker_address, 1883)

print("가상 스마트팜 보드 가동 시작...")

while True:
    # 데이터 생성 (요청하신 데이터 타입 준수)
    data = {
        "sensor_id": "esp32_01",
        "temp": round(20 + random.uniform(-2, 5), 2),        # Float
        "humidity": round(40 + random.uniform(-5, 10), 2),    # Float
        "soil_moisture": round(30 + random.uniform(-10, 20), 2), # Float
        "co2": random.randint(400, 1000),                    # Int
        "light": random.randint(0, 1000)                     # Int (일조량)
    }

    payload = json.dumps(data)
    client.publish(topic, payload)
    print(f"데이터 전송: {payload}")
    time.sleep(1)