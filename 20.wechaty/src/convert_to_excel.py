import pandas as pd

# 读取文本文件
with open(r'D:\work\github\labs\20.wechaty\src\DTAIGC-QA01-20250226.txt', 'r') as file:
    lines = file.readlines()

# 将文本数据转换为DataFrame
data = []
for line in lines:
    # 假设每行数据以空格分隔，可以根据实际情况调整
    data.append(line.strip().split())

# 创建DataFrame
df = pd.DataFrame(data)

# 将DataFrame保存为Excel文件
df.to_excel('C:/Users/21452\Desktop/temp/DTAIGC-QA01-20250226.xlsx', index=False, header=False)