import Helper

lines, N, Q, queries, calculated_hashes = Helper.init_values(100000, 100000)

candidates = {}
for b in range(1, 9):
    slots = {}
    for curr_id in range(int(N)):
        _hash = Helper.get_bytes(b, calculated_hashes[curr_id])
        val = Helper.hash_2_int(_hash)
        txt_in_slot = set()
        if val in slots:
            txt_in_slot = slots[val]
            for txt_id in txt_in_slot:
                if curr_id not in candidates:
                    candidates[curr_id] = set()
                    candidates[curr_id].add(txt_id)
                else:
                    candidates[curr_id].add(txt_id)
                if txt_id not in candidates:
                    candidates[txt_id] = set()
                    candidates[txt_id].add(curr_id)
                else:
                    candidates[txt_id].add(curr_id)
        else:
            txt_in_slot = set()
        txt_in_slot.add(curr_id)
        slots[val] = txt_in_slot

for q_index, query in enumerate(queries):
    I, K = query.split()
    count = 0
    if not candidates.get(int(I)) is None:
        for candidate in candidates.get(int(I)):
            count = Helper.count_similar(calculated_hashes[int(I)], calculated_hashes[candidate], K, count)
    print(count)


