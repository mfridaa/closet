//
//  DatePickerTableViewCell.swift
//  Trax
//
//  Created by Kiss Levente on 2018. 04. 19..
//  Copyright © 2018. Kiss Levente. All rights reserved.
//

import UIKit

class DatePickerTableViewCell: UITableViewCell {

    @IBOutlet weak var dayOfWeek: UILabel!
    
    @IBOutlet weak var openDate: UIDatePicker!
    
    @IBOutlet weak var closeDate: UIDatePicker!
    
    @IBOutlet var openinHours: UIStackView!
    
//    var opens:Date?
    
    var openingSetted:((_ input :Date,_ name:String)->Void)?
    
    var closingSetted:((_ input :Date,_ name:String)->Void)?
    
    var dayRemoved:((_ name:String)->Void)?
    
    @IBAction func openDateSet(_ sender: UIDatePicker) {
        let opens = sender.date
        if let function = openingSetted{
            function(opens,day!)
        }
        
    }
    @IBAction func closeDateSet(_ sender: UIDatePicker) {
        let close = sender.date
        if let function = closingSetted{
            function(close,day!)
        }
    
    }
    
   
    
    
    @IBAction func openingStatus(_ sender: UISwitch) {
        if !sender.isOn {
            openinHours.removeFromSuperview()
            if let function = dayRemoved{
                function(day!)
            }
  
        }else{
            cell.addArrangedSubview(openinHours)

        }
        
    }
    
    @IBOutlet weak var cell: UIStackView!
    
    var day:String?{
        get{
            return dayOfWeek.text
        }
        set{
            dayOfWeek.text = newValue
        }
    }
//    var opens:Date{
//        get{
//            return openDate.date
//        }
//    }
    
    var close:Date{
        get{
            return closeDate.date
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
