import paho.mqtt.client as mqtt
import time
import random
import json
import os
import sys

# 환경 변수에서 브로커 주소 가져오기 (기본값: localhost)
# 클라우드/도커 환경에서는 'mosquitto' 서비스 이름을 사용하게 됨
broker_address = os.getenv("BROKER_URL", "localhost")
broker_port = int(os.getenv("BROKER_PORT", 1883))
topic = "home/livingroom/temp"

print(f"Connecting to MQTT Broker at {broker_address}:{broker_port}...")

# MQTT 클라이언트 설정 (CallbackAPIVersion.VERSION2 필수)
client = mqtt.Client(mqtt.CallbackAPIVersion.VERSION2, "Cloud_Publisher")

# 연결 재시도 로직
while True:
    try:
        client.connect(broker_address, broker_port)
        print("Connected to MQTT Broker!")
        break
    except Exception as e:
        print(f"Connection failed: {e}. Retrying in 5 seconds...")
        time.sleep(5)

client.loop_start()

print(f"Start publishing to {topic}...")

try:
    while True:
        # 가짜 데이터 생성
        fake_temp = 20 + random.uniform(-2, 5)
        # Frontend expects: temperature, humidity, soilMoisture, co2, lightIntensity
        # But for now test.py only sent 'temp' and 'sensor_id'.
        # Updating to send more data for a better demo if desired, 
        # but sticking to original logic first to ensure compatibility, 
        # just adding fields if the backend expects them. 
        # The backend maps 'temp' -> 'temperature'.
        
        payload_dict = {
            "sensor_id": "esp32_cloud_01",
            "temp": round(fake_temp, 2),
            # Optional: Add other fields if we want the dashboard to look more alive
            "humidity": round(50 + random.uniform(-10, 10), 1),
            "co2": int(400 + random.uniform(0, 100)),
            "light": int(800 + random.uniform(-100, 100))
        }
        
        payload = json.dumps(payload_dict)
        client.publish(topic, payload)
        print(f"Published: {payload}")

        time.sleep(2) # 2초마다 전송

except KeyboardInterrupt:
    print("Stopping publisher...")
    client.loop_stop()
    client.disconnect()
