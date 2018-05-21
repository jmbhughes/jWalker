import numpy as np
import matplotlib.pyplot as plt
import argparse

def get_args():
    ap = argparse.ArgumentParser()
    ap.add_argument("path", help="path to text file to open")
    return vars(ap.parse_args())

if __name__ == "__main__":
    args = get_args()
    dat = np.loadtxt(args['path'])
    m3d, rg = dat[:,0], dat[:,1]

    fig, ax = plt.subplots()
    ax.plot(np.log10(rg),
            np.log10(m3d),
            ".")
    plt.show()
