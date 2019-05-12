//
//  SubViewController.swift
//  Temperature Convertor
//
//  Created by Alagar Nammalvar on 5/10/19.
//  Copyright © 2019 SJSU. All rights reserved.
//

import UIKit

class SubViewController: UIViewController {

    @IBOutlet weak var outputf: UILabel!
    var outputval = ""
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
           outputf.text = outputval
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
