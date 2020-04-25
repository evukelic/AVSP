import hashlib
import numpy as np
import sys


def init_values(max_n, max_q):
    lines = np.array(sys.stdin.read().splitlines())
    N = int(lines[0])
    Q = int(lines[N + 1])

    if N > max_n or Q > max_q:
        sys.stderr.write('Invalid argument size!')
        exit()

    queries = lines[N + 2:N + 2 + Q]
    calculated_hashes = calc_hash(lines, N)

    return lines, N, Q, queries, calculated_hashes


def get_units(text):
    return text.lower().split()


def concat(elems):
    result = ''
    for elem in elems:
        result += str(elem)
    return result


def calc_hash(lines, N):
    res = np.array([simhash(lines[i]) for i in range(1, int(N)+1)])
    return res


def simhash(text):
    sh = np.zeros((128,), dtype=int)
    units = get_units(text)

    for unit in units:
        hashhex = hashlib.md5(unit.encode('utf-8')).hexdigest()
        _hash = list(bin(int(hashhex, base=16))[2:].rjust(128, "0"))
        sh = np.array([sh[i]+1 if h == '1' else sh[i]-1 for i, h in enumerate(_hash)])

    sh = np.array([1 if elem >= 0 else 0 for i, elem in enumerate(sh)])

    result = concat(sh)
    resulthex = hex(int(result, 2))[2:]

    return resulthex


def hamming_distance(s1, s2):
    assert len(s1) == len(s2)
    return sum(c1 != c2 for c1, c2 in zip(s1, s2))


def get_bytes(band, s):
    range1 = (band - 1) * 16;
    range2 = (band * 16) - 1;
    _bytes = bin(int(s, base=16))[2:].rjust(128, "0")
    return _bytes[range1:range2 + 1]


def count_similar(hash1, hash2, K, count):
    first = bin(int(hash1, base=16))[2:].rjust(128, "0")
    second = bin(int(hash2, base=16))[2:].rjust(128, "0")
    dist = hamming_distance(first, second)
    if dist <= int(K):
        count += 1
    return count


def hash_2_int(num):
    return int(num, 2)
