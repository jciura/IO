import React, { useEffect, useState } from "react";

const CsvTablePreview = ({ file }) => {
    const [csvData, setCsvData] = useState([]);

    useEffect(() => {
        const parseCsv = async () => {
            if (!file) {
                setCsvData([]);
                return;
            }

            const text = await file.text();
            const lines = text.trim().split("\n");
            const rows = lines.map(line => line.split(";").map(cell => cell.trim()));
            setCsvData(rows);
        };

        parseCsv();
    }, [file]);

    if (!file || csvData.length === 0) {
        return null;
    }

    return (
        <div className="table-responsive mt-3">
            <h5>Podgląd pliku:</h5>
            <table className="table table-bordered table-sm">
                <thead>
                    <tr>
                        {csvData[0].map(col => (
                            <th>{col}</th>
                        ))}
                    </tr>
                </thead>
                <tbody>
                    {csvData.slice(1).map(row => (
                        <tr>
                            {row.map(cell => (
                                <td>{cell}</td>
                            ))}
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default CsvTablePreview;
