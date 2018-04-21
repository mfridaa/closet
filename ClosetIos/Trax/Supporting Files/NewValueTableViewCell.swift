//
//  NewValueTableViewCell.swift
//  Trax
//
//  Created by Kiss Levente on 2018. 04. 19..
//  Copyright © 2018. Kiss Levente. All rights reserved.
//

import UIKit

class NewValueTableViewCell: UITableViewCell {

    @IBOutlet weak var cellName: UILabel!
    
//    @IBOutlet weak var inputName: UITextField!
    
    @IBOutlet private weak var newName: UITextField!

    
    @IBAction func newValueEntered(_ sender: UITextField) {
        
        if let function = changeHappened{
            function(newName.text!)
        }
    }
    

    var changeHappened:((_ input :String)->Void)?
    
    var name:String?{
        set{
            cellName.text = newValue
            
            
        }
        get{
            return cellName.text
        }
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
