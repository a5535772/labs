import pandas as pd

# 定义文件路径
file_path = "C:/Users/21452/Desktop/temp/DTAIGC-QA01-20250226.txt"
excel_path = "C:/Users/21452/Desktop/temp/1.xlsx"

# 定义列名
columns = ["名称", "命名空间", "CPU请求", "CPU限制", "内存请求", "内存限制", "运行时间"]

# 使用 pandas 读取数据
df = pd.read_csv(file_path, delim_whitespace=True, header=None, names=columns)

# 将结果保存为 Excel 文件
df.to_excel(excel_path, index=False)

print(f"已将文件 {file_path} 转换为 Excel 文件，并保存到 {excel_path}")