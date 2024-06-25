import sys

def conv(input_filename, output_filename):
    with open(input_filename, "r+") as input_file, open(output_filename, "w") as output_file:
        for line in input_file:
            cleaned_line = line.strip().lstrip("[").rstrip("]").rstrip(",") # just want object "{keys: values}", 
                                                                            # stripping outer whitespace, square brackets and commas is always valid
            if cleaned_line.strip() == "":
                continue # skip empty lines
            print(cleaned_line, file=output_file)
                

if __name__ == "__main__":
    if len(sys.argv) < 3:
        print("Need args: input_filename output_filename")
    else:
        conv(sys.argv[1], sys.argv[2])
