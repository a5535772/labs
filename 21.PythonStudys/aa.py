# 导入json模块，用于处理JSON数据
import json

# 定义文件路径
file_path = "D:/aa.txt"  # 假设JSON数据存储在data.json文件中

#读取文件中的每一行，并打印
with open(file_path, 'r', encoding='utf-8') as file:
    for line in file:
        linenew=line.replace("data: ","")
        obj=json.loads(linenew)

        print(obj["choices"][0]["delta"]["content"])