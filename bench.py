import argh
import os
import subprocess
import random

from tqdm import trange


@argh.arg('--number', '-n', help='the number of file to test.')
@argh.arg('--out', '-o', help='the out file where the results will be written.')
@argh.arg('--input-dir', '-i', help='the dir where the input datas are stored.')
def test(number=100, out='data.csv', input_dir='cartes'):
    """
    Test n random input files and write the exec times in the out file.
    """
    input_list = os.listdir(input_dir)
    created = not os.path.exists(out)

    with open(out, 'a') as out_file:
        if created:
            out_file.write('file,segments,intersections,time\n')
        for i in trange(number, desc="tests"):
            test_file = random.choice(input_list)
            file_path = os.path.join(input_dir, test_file)

            result = subprocess.check_output(['gradle', '-q', 'run',
                                              f'--args="{file_path}" --bench'])

            out_file.write(f'"{test_file}",{result.decode()}')


if __name__ == "__main__":
    argh.dispatch_command(test)
