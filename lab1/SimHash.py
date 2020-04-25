import Helper

lines, N, Q, queries, calculated_hashes = Helper.init_values(1000, 1000)

for q_index, query in enumerate(queries):
    I, K = query.split()
    count = 0
    cmp = calculated_hashes[int(I)]
    for h_index, hsh in enumerate(calculated_hashes):
        if h_index != int(I):
            count = Helper.count_similar(hsh, cmp, K, count)
    print(count)

